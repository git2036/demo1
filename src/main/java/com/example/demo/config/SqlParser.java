package com.example.demo.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {
    public static String getTableNameFromSql(String sql) {
        // 简单的正则表达式匹配，适用于简单的 SELECT 语句
        Pattern pattern = Pattern.compile("FROM\\s+(\\w+)");
        Matcher matcher = pattern.matcher(sql.toUpperCase());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}