package com.test.springboot.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 *
 * @author LJ
 * @version V1.0
 * @date 2015年9月26日 下午1:03:14
 */
public class StringUtil {
    /**
     * 方法名称:transMapToString 传入参数:map 返回值:String 形如 username'chenziwen^password'1234
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuilder sb = new StringBuilder();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString().trim()).append(':').append(entry.getValue().toString().trim())
                    .append(iterator.hasNext() ? "," : "");
        }
        return sb.toString();
    }

    /**
     *
     * 方法名称:transStringToMap
     *
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     *
     * 返回值:Map
     *
     */

    public static Map transStringToMap(String mapString) {

        Map<String,Object> map = new HashMap<>();

        StringTokenizer items;

        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();

             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))

            items = new StringTokenizer(entrys.nextToken(), "'");

        return map;

    }
}
