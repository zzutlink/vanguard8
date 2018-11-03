package com.vanguard8.framework.service;

import com.vanguard8.common.Result;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.entity.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DeptstaService {
    public List<Deptsta> getNextLevel(Integer dsId);

    public Result<Integer> saveDeptsta(String playFlag, Integer dsId, String dsName, User user) throws DataAccessException;
}
