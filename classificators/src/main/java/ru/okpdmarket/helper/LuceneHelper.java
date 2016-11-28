package ru.okpdmarket.helper;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by frostymaster on 28.11.2016.
 */
public interface LuceneHelper {

    void indexDirectory();

    void search(String text);

}
