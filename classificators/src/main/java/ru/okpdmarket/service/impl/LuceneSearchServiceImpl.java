package ru.okpdmarket.service.impl;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
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
            //TODO: add working path
            Path path = Paths.get("IMPLEMENT NORMAL PATH");
            IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
            IndexWriter indexWriter = new IndexWriter(idx, config);
            indexWriter.deleteAll();
            //TODO: add working path

            indexWriter.close();
            idx.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public List<ClassificatorItem> search(String classificatorId, String query) {
        try {
            IndexReader indexReader = DirectoryReader.open(idx);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
}
