package com.vanguard8.base.service;

import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface BaseService {

    BaseMain getBaseMain(Integer bsId);

    Result<String> saveBaseMain(String playFlag, BaseMain main);

    EasyUIDataGrid getAllBaseMain(Integer page, Integer rows, String sort, String order, BaseMain main);

    List<BaseDetail> getBaseDetail(Integer bsId);

    List<Map<String,Object>> executeSelect(Integer page, Integer rows, String sort, String order, Integer bsId, String paramName, String paramValue);

    Integer executeCount(Integer bsId, String paramName, String paramValue);

    Result<String> save(Integer bsId, String playFlag, HashMap<String, String> maps);

    Result<String> saveBaseDetail(Integer playFlag, BaseDetail detail);
}
