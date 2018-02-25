package ru.okpdmarket.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.SearchResult;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.service.SearchService;
import ru.okpdmarket.service.exception.SearchServiceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frostymaster on 28.11.2016.
 */
@Service
public class LuceneSearchServiceImpl implements SearchService {
    private static final String CONTENT_FIELD = "content";
    private static final String CLS_ID = "classificator_code";
    private static final String ITEM_ID = "item_code";
    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private Integer searchHitsLimit;

    private final ClassificatorRepository repository;

    RAMDirectory idx = new RAMDirectory();

    public LuceneSearchServiceImpl(ClassificatorRepository repository, @Value("${application.search.hits.limit}") Integer searchHitsLimit) {
        this.repository = repository;
        this.searchHitsLimit = searchHitsLimit;
    }

    private Document createDocument(ClassificatorItem item) {
        Document doc = new Document();

        doc.add(new TextField(CLS_ID, item.getClassificatorCode(), Field.Store.YES));
        doc.add(new TextField(ITEM_ID, item.getCode(), Field.Store.YES));

        doc.add(new TextField(CONTENT_FIELD, item.getCode(), Field.Store.NO));
        doc.add(new TextField(CONTENT_FIELD, item.getName(), Field.Store.NO));
        doc.add(new TextField(CONTENT_FIELD, item.getNotes(), Field.Store.NO));

        return doc;
    }

    @Override
    public void indexClassificator(Classificator classificator) {
        try {
            //indexing directory
            List<Document> docs = new ArrayList<>();
            IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
            IndexWriter indexWriter = new IndexWriter(idx, config);
            classificator.getContents().traverseItems((i) -> docs.add(createDocument(i)));
            indexWriter.addDocuments(docs);
            indexWriter.close();
        } catch (IOException e) {
            log.error("Error indexing classificator " + classificator.getCode(), e);
        }
    }

    @Override
    public SearchResult searchByClassificator(String classificatorId, String queryString, Integer take, Integer offset) {
        List<ClassificatorItem> results = new ArrayList<>();
        try {
            IndexReader indexReader = DirectoryReader.open(idx);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new StandardAnalyzer();

            // Build a Query object
            QueryParser parser = new QueryParser(CONTENT_FIELD, analyzer);
            Query query = parser.parse(queryString);

            QueryParser clsQP = new QueryParser(CLS_ID, analyzer);
            Query clsFilter = clsQP.parse(classificatorId);

            BooleanQuery finalQuery = new BooleanQuery();
            finalQuery.add(query, BooleanClause.Occur.MUST);
            finalQuery.add(clsFilter, BooleanClause.Occur.MUST);

            TopScoreDocCollector collector = TopScoreDocCollector.create(searchHitsLimit);

            // Search for the query
            indexSearcher.search(finalQuery, collector);
            TopDocs topDocs = collector.topDocs(offset, take);


            // Examine the Hits object to see if there were any matches
            int totalHitCount = topDocs.totalHits;
            int resultSize = topDocs.scoreDocs.length;
            if (totalHitCount > 0) {
                log.debug("Hits for \"" + queryString + "\" were found in quotes by:");
                // Iterate over the Documents in the Hits object
                for (int i = 0; i < resultSize; i++) {
                    ScoreDoc scoreDoc = topDocs.scoreDocs[i];
                    Document doc = indexSearcher.doc(scoreDoc.doc);
                    results.add(getClassificatorItem(doc));
                }
            }
            return new SearchResult(results, totalHitCount);

        } catch (IOException | ParseException ie) {
            throw new SearchServiceException(ie);
        }
    }

    private ClassificatorItem getClassificatorItem(Document doc) {
        return repository.getClassificatorByCode(doc.get(CLS_ID)).getContents().getItemByCode(doc.get(ITEM_ID));
    }

}


