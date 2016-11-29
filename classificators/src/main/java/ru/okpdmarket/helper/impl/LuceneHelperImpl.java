package ru.okpdmarket.helper.impl;

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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.helper.LuceneHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by frostymaster on 28.11.2016.
 */
@Service
public class LuceneHelperImpl implements LuceneHelper {

    @Autowired
    RAMDirectory idx;

    @Override
    public void indexDirectory() {
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
    public void search(String text) {
        try {
            IndexReader indexReader =  DirectoryReader.open(idx);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
