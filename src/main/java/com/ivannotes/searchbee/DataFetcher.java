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
package com.ivannotes.searchbee;

import java.util.List;

/**
 * 该类用来获取数据创建索引
 * 
 * @author miracle.ivanlee@gmail.com since Aug 13, 2012
 * 
 * @param <T>
 */
public interface DataFetcher<T> {

    /**
     * 是否还有数据可以获取
     * 
     * @return true if has more data to fetch, otherwise false
     */
    public boolean hasMore();

    /**
     * 获取数据
     * 
     * @return List<T> 如果没有取到数据返回大小为空的List
     */
    public List<T> fetchData();

    /**
     * 重置获取器的指针，下一次调用{@link #fetchData()}重头开始获取
     */
    public void reset();
}
