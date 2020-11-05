package com.vanguard8.framework.service;

import com.vanguard8.common.Result;
import com.vanguard8.common.SessionUser;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.entity.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DeptstaService {
    List<Deptsta> getNextLevel(Integer dsId);

    Result<Integer> saveDeptsta(String playFlag, Integer dsId, String dsName, SessionUser user) throws DataAccessException;

    List<Function> getFuncsWithRight(Integer dsId);

    Result<String> saveStatFunc(Integer dsId, String funcStr)  throws DataAccessException;

    boolean checkActionRight(Integer dsId, String url);
}
