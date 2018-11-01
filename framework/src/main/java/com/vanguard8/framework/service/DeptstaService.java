package com.vanguard8.framework.service;

import com.vanguard8.framework.entity.Deptsta;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DeptstaService {
    public List<Deptsta> getNextLevel(Integer dsId);

    public Integer saveDeptsta(String playFlag, Integer dsId, String dsName) throws DataAccessException;
}
