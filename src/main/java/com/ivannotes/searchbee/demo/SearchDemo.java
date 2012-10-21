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

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

import com.ivannotes.searchbee.PaginationResult;

/**
 * @author miracle.ivanlee@gmail.com since Aug 14, 2012
 * 
 */
public class SearchDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        TxtSearchBee searchBee = new TxtSearchBee();
        TxtDataFetcher df = new TxtDataFetcher();

        try {
            searchBee.doIndex(df);
            //            List<Document> docs = searchBee.query("格瓦拉");
            PaginationResult result = searchBee.doPaginationQuery("7", 0, 5,
                    new TxtSearchBee.TermQueryBuilder());
            List<Document> docs = result.getDocs();

            for (Document doc : docs) {
                System.out.println("==> " + doc.get("txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
