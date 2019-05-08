package com.vanguard8.framework.dao;

import com.vanguard8.framework.entity.Deptsta;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DeptstaDao {
    int deleteByPrimaryKey(Integer dsId);

    Integer insert(Deptsta record) throws DataAccessException;

    int insertSelective(Deptsta record);

    Deptsta selectByPrimaryKey(Integer dsId);

    int updateByPrimaryKeySelective(Deptsta record);

    int updateByPrimaryKey(Deptsta record);

    List<Deptsta> selectNextLevel(Integer dsId);

    List<String> selectMaxCode(String dsCode);

    List<Deptsta> selectByDsCode(String dsCode);
}