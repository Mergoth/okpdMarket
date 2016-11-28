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
import org.apache.lucene.util.Version;
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
public class LuceneHelperImpl implements LuceneHelper {
    @Override
    public void indexDirectory() {
        try {
            //indexing directory
            //TODO: add working path
            Path path = Paths.get("IMPLEMENT NORMAL PATH");
            Directory directory = FSDirectory.open(path);
            IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
            IndexWriter indexWriter = new IndexWriter(directory, config);
            indexWriter.deleteAll();
            //TODO: add working path
            File f = new File("IMPLEMENT NORMAL PATH"); // current directory
            for (File file : f.listFiles()) {
                System.out.println("indexed " + file.getCanonicalPath());
                Document doc = new Document();
                doc.add(new TextField("path", file.getName(), Field.Store.YES));
                FileInputStream is = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while((line = reader.readLine())!=null){
                    stringBuffer.append(line).append("\n");
                }
                reader.close();
                doc.add(new TextField("contents", stringBuffer.toString(), Field.Store.YES));
                indexWriter.addDocument(doc);
            }
            indexWriter.close();
            directory.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void search(String text) {
        try {
            Path path = Paths.get("C:/Users/Tuna/Desktop/lucene-5.1.0/indexes");
            Directory directory = FSDirectory.open(path);
            IndexReader indexReader =  DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            QueryParser queryParser = new QueryParser("contents",  new StandardAnalyzer());
            Query query = queryParser.parse(text);
            TopDocs topDocs = indexSearcher.search(query,10);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document document = indexSearcher.doc(scoreDoc.doc);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
