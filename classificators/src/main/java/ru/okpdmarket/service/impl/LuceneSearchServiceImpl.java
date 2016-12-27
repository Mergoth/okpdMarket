package ru.okpdmarket.service.impl;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.SearchService;

import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by frostymaster on 28.11.2016.
 */
@Service
public class LuceneSearchServiceImpl implements SearchService {

    RAMDirectory idx = new RAMDirectory();

    @Override
    public void indexClassificator(Classificator classificator) {
        try {
            //indexing directory

            IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
            IndexWriter indexWriter = new IndexWriter(idx, config);
            indexWriter.addDocument(createDocument(classificator.getId(),classificator.getName()));

            indexWriter.close();
            //idx.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public List<ClassificatorItem> findClassificator(String classificatorId, String query) {
        try {
            IndexReader indexReader = DirectoryReader.open(idx);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);



        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    private static Document createDocument(String id, String name)
    {
        Document doc = new Document();
        // Add the title as an unindexed fieldâ€¦
        doc.add(new Field("title", id, Field.Store.YES, Field.Index.NO));
        // â€¦and the content as an indexed field. Note that indexed
        // Text fields are constructed using a Reader. Lucene can read
        // and index very large chunks of text, without storing the
        // entire content verbatim in the index. In this example we
        // can just wrap the content string in a StringReader.
        doc.add(new Field("content", new StringReader(name)));
        return doc;
    }


}
