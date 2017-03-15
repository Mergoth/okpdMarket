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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.SearchService;

import java.io.StringReader;
import java.util.List;

/**
 * Created by frostymaster on 28.11.2016.
 */
@Service
public class LuceneSearchServiceImpl implements SearchService {

    public static final String CONTENT_FIELD = "content";
    public static final String CLS_ID = "classificator_code";
    public static final String ITEM_ID = "item_code";
    RAMDirectory idx = new RAMDirectory();

    private static Document createDocument(ClassificatorItem item) {
        Document doc = new Document();
        // Add the title as an unindexed fieldâ€¦
        doc.add(new TextField(CLS_ID, item.getClassificatorCode(), Field.Store.YES));
        doc.add(new TextField(ITEM_ID, item.getCode(), Field.Store.YES));

        // â€¦and the content as an indexed field. Note that indexed
        // Text fields are constructed using a Reader. Lucene can read
        // and index very large chunks of text, without storing the
        // entire content verbatim in the index. In this example we
        // can just wrap the content string in a StringReader.
        doc.add(new TextField(CONTENT_FIELD, new StringReader(item.getCode())));
        doc.add(new TextField(CONTENT_FIELD, new StringReader(item.getName())));
        doc.add(new TextField(CONTENT_FIELD, new StringReader(item.getNotes())));
        return doc;
    }

    @Override
    public void indexClassificator(Classificator classificator) {
        try {
            //indexing directory

            IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
            IndexWriter indexWriter = new IndexWriter(idx, config);
            classificator.getContents().traverseItems(LuceneSearchServiceImpl::createDocument);
            indexWriter.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public List<ClassificatorItem> searchByClassificator(String classificatorId, String queryString) {
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
            if (hitCount == 0) {
                System.out.println("No matches were found for \"" + queryString + "\"");
            } else {
                System.out.println("Hits for \"" + queryString
                        + "\" were found in quotes by:");
                // Iterate over the Documents in the Hits object
                for (int i = 0; i < hitCount; i++) {
                    ScoreDoc doc = topDocs.scoreDocs[i];
                    // Print the value that we stored in the "title" field. Note
                    // that this Field was not indexed, but (unlike the
                    // "contents" field) was stored verbatim and can be
                    // retrieved.
                    System.out.println(" " + (i + 1) + ". " + doc.toString());
                }
            }
            System.out.println();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }


}



