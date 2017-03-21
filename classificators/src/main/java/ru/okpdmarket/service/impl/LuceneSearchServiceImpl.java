package ru.okpdmarket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
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
    public static final String CONTENT_FIELD = "content";
    public static final String CLS_ID = "classificator_code";
    public static final String ITEM_ID = "item_code";
    private static final Logger log = LoggerFactory.getLogger(SearchService.class);
    private static final String ITEM = "item";
    RAMDirectory idx = new RAMDirectory();
    ObjectMapper objectMapper = new ObjectMapper();

    private Document createDocument(ClassificatorItem item) {
        Document doc = new Document();

        doc.add(new TextField(CLS_ID, item.getClassificatorCode(), Field.Store.YES));
        doc.add(new TextField(ITEM_ID, item.getCode(), Field.Store.YES));

        doc.add(new TextField(CONTENT_FIELD, item.getCode(), Field.Store.NO));
        doc.add(new TextField(CONTENT_FIELD, item.getName(), Field.Store.NO));
        doc.add(new TextField(CONTENT_FIELD, item.getNotes(), Field.Store.NO));
        try {
            doc.add(new StoredField(ITEM, objectMapper.writeValueAsBytes(item)));
        } catch (JsonProcessingException e) {
            log.error("Error serializing item: " + item.getClassificatorCode() + "/" + item.getCode(), e);
        }
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
    public List<ClassificatorItem> searchByClassificator(String classificatorId, String queryString) {
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

            // Search for the query
            TopDocs topDocs = indexSearcher.search(finalQuery, 100);
            // Examine the Hits object to see if there were any matches
            int hitCount = topDocs.totalHits;
            if (hitCount > 0) {
                log.debug("Hits for \"" + queryString
                        + "\" were found in quotes by:");
                // Iterate over the Documents in the Hits object
                for (int i = 0; i < hitCount; i++) {
                    ScoreDoc scoreDoc = topDocs.scoreDocs[i];
                    Document doc = indexSearcher.doc(scoreDoc.doc);
                    results.add(getClassificatorItem(doc));
                }
            }
        } catch (IOException | ParseException ie) {
            throw new SearchServiceException(ie);
        }
        return results;
    }

    private ClassificatorItem getClassificatorItem(Document doc) {
        try {
            return objectMapper.readValue(doc.getBinaryValue(ITEM).bytes, ClassificatorItem.class);
        } catch (IOException e) {
            throw new SearchServiceException("Error deserializing item: " + doc.get(ITEM_ID), e);
        }
    }


}



