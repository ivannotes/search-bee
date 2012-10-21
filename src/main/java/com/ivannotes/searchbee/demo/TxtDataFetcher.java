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

import java.util.ArrayList;
import java.util.List;

import com.ivannotes.searchbee.DataFetcher;

/**
 * @author miracle.ivanlee@gmail.com since Aug 14, 2012
 * 
 */
public class TxtDataFetcher implements DataFetcher<String> {

    private boolean hasMore = true;

    private int recordNum = 0;

    private String[] testWords = new String[] { "仙剑奇侠传三", "剑雨", "IvanLee's 猜想", "首尔" };

    int i = 0;

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    @Override
    public List<String> fetchData() {
        recordNum += 3;
        if (recordNum > 1000) {
            hasMore = false;
        }

        List<String> result = new ArrayList<String>();
        result.add(testWords[i++ % testWords.length] + recordNum);
        return result;
    }

    @Override
    public void reset() {
        recordNum = 0;
        hasMore = true;
    }

}
