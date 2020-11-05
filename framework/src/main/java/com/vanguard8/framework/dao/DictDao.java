package com.vanguard8.framework.dao;


import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;

import java.util.List;

public interface DictDao {
    List<Dict> executeSelect(String sql);

    String getDictSql(Integer dictId);

    String getBussDictSql(Integer dictId);
}
