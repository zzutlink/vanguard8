package com.vanguard8.common;

import java.util.List;
import java.util.Map;

public class DataGridJson {
    private Integer total;
    private List<Map<String,Object>> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
