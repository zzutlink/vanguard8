package com.vanguard8.base.dao;

import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface BaseDao {
    BaseMain getBaseMain(Integer bsId);

    List<BaseMain> getAllBaseMain(BaseMain main);

    Integer insertBaseMain(BaseMain main);

    Integer updateBaseMain(@Param("oBsId") Integer oBsId, @Param("base") BaseMain main);

    Integer deleteBaseMain(Integer bsId);

    Integer insertBaseDetail(BaseDetail detail);

    Integer updateBaseDetail(BaseDetail detail);

    Integer deleteBaseDetailById(Integer detailId);

    Integer deleteBaseDetail(Integer bsId);

    List<BaseDetail> getBaseDetail(Integer bsId);

    List<Map<String,Object>> executeSelect(String sql);

    Integer executeCount(String sql);

    Integer executeInsert(String sql) throws DataAccessException;

    Integer executeUpdate(String sql) throws DataAccessException;

    Integer executeDelete(String sql) throws DataAccessException;

    String selectMaxId(String sql);

    Integer updateDetailBsId(@Param("nBsId") Integer nBsId,@Param("bsId") Integer bsId);
}
