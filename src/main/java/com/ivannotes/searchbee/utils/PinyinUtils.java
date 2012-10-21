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
package com.ivannotes.searchbee.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 将汉字转换为拼音的工具
 * 
 * @author miracle.ivanlee@gmail.com since Aug 13, 2012
 * 
 */
public class PinyinUtils {

    private static HanyuPinyinOutputFormat outFormat = new HanyuPinyinOutputFormat();
    static {
        outFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        outFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 将汉语字符串转化为拼音。
     * <p>
     * 该方法支持中英文混拼。如：“ivanlee's 猜想”转成全拼后为 ==> “Ivanlee's caixiang”<br>
     * 该方法支持获取拼音首字母。如：“ivanlee's 猜想”转成全拼后为 ==> “Ivanlee's cx”<br>
     * 
     * @param txt 需要转化的文本
     * @param headOnly 是否只需要拼音首字母。true 代表只获取首字母, false代表获取全拼
     * @return 转化后的拼音字串
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPinyin(String txt, boolean headOnly)
            throws BadHanyuPinyinOutputFormatCombination {
        char[] chars = txt.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(chars[i], outFormat);
            if (null == pinyin || pinyin.length == 0) {
                sb.append(chars[i]);
            } else {
                if (headOnly) {
                    sb.append(pinyin[0].charAt(0));
                } else {
                    sb.append(pinyin[0]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param args
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        System.out.println(getPinyin("Ivanlee's 猜想", false));
    }

}
