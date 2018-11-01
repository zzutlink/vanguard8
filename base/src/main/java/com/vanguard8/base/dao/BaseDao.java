package com.vanguard8.base.dao;

import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface BaseDao {
    BaseMain getBaseMain(Integer bsId);

    List<BaseDetail> getBaseDetail(Integer bsId);

    List<Map<String,Object>> executeSelect(String sql);

    Integer executeCount(String sql);

    Integer executeInsert(String sql);

    Integer executeUpdate(String sql);

    Integer executeDelete(String sql);

    String selectMaxId(String sql);
}
