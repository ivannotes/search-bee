/*
 * Copyright 2012 www.ivannotes.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.ivannotes.searchbee.demo;

import java.io.File;
import java.io.IOException;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.ivannotes.searchbee.SearchBee;
import com.ivannotes.searchbee.utils.PinyinUtils;

/**
 * @author miracle.ivanlee@gmail.com since Aug 14, 2012
 * 
 */
public class TxtSearchBee extends SearchBee<String> {

    private String idxPath = "/Users/ivanlee/Documents/Temp/bee";

    private final static Version matchVersion = Version.LUCENE_36;

    private static int i = 1;

    //    private Analyzer analyzer = new SmartChineseAnalyzer(matchVersion);

    private final static Analyzer analyzer = new StandardAnalyzer(matchVersion);

    @Override
    protected IndexWriter getIndexWriter() {
        Directory dir;
        try {
            dir = FSDirectory.open(new File(idxPath));
            IndexWriterConfig idxCfg = new IndexWriterConfig(matchVersion, analyzer);
            idxCfg.setOpenMode(OpenMode.CREATE);
            IndexWriter idxWriter = new IndexWriter(dir, idxCfg);

            return idxWriter;
        } catch (IOException e) {
            throw new RuntimeException("Create index writer error. ", e);
        }
    }

    @Override
    protected Document buildDocument(String bean) {
        Document doc = new Document();
        Field txt = new Field("txt", bean, Store.YES, Field.Index.ANALYZED);
        txt.setIndexOptions(IndexOptions.DOCS_ONLY);
        doc.add(txt);

        try {
            Field pinyinTxt = new Field("pinyinTxt", PinyinUtils.getPinyin(bean, false), Store.NO,
                    Field.Index.NOT_ANALYZED);
            doc.add(pinyinTxt);

            Field uid = new Field("uid", (i++) + "", Store.NO, Field.Index.NOT_ANALYZED);
            System.out.println(i);
            doc.add(uid);

            Field shortPinyinTxt = new Field("shortPinyinTxt", PinyinUtils.getPinyin(bean, true),
                    Store.NO, Field.Index.NOT_ANALYZED);
            doc.add(shortPinyinTxt);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return doc;
    }

    public static class TxtQueryBuilder implements QueryBuilder {

        @Override
        public Query buildQuery(String keyWords) {
            // TODO Auto-generated method stub
            BooleanQuery query = new BooleanQuery();
            try {
                QueryParser parser = new QueryParser(matchVersion, "txt", analyzer);
                parser.setDefaultOperator(Operator.AND);
                Query txtQuery = parser.parse(keyWords);
                query.add(txtQuery, Occur.SHOULD);

                //            TermQuery tQuery = new TermQuery(new Term("txt", keyWords));
                //            query.add(tQuery, Occur.SHOULD);

                PrefixQuery pinyinQuery = new PrefixQuery(new Term("pinyinTxt", keyWords));
                query.add(pinyinQuery, Occur.SHOULD);

                PrefixQuery shortPinyinQuery = new PrefixQuery(new Term("shortPinyinTxt", keyWords));
                query.add(shortPinyinQuery, Occur.SHOULD);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return query;
        }

    }

    public static class TermQueryBuilder implements QueryBuilder {

        @Override
        public Query buildQuery(String keyWords) {
            Query query = new TermQuery(new Term("uid", keyWords));
            return query;
        }

    }

    @Override
    protected IndexSearcher getIndexSearcher() {
        try {
            IndexReader reader;
            reader = IndexReader.open(FSDirectory.open(new File(idxPath)));
            IndexSearcher searcher = new IndexSearcher(reader);

            return searcher;
        } catch (Exception e) {
            throw new RuntimeException("Get index searcher error. ", e);
        }
    }

}
