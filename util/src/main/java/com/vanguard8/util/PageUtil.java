package com.vanguard8.util;

public class PageUtil {

    //将sql语句处理为分页的sql
    // sql 原sql语句  page 页码 rows 每页数量 sort 排序字段 order 排序规则asc或desc
    public static String pageSql(String sql, Integer page, Integer rows, String sort, String order) {
        if (sort != null && sort.length() != 0) {
            sql += " order by " + sort + " " + order;
        }
        sql += " limit " + Integer.toString((page - 1) * rows) + "," + Integer.toString(rows);
        return sql;
    }
}
