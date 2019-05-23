package com.vanguard8.search.service;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.search.entity.SrhModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ModelService {
    SrhModel getModelForClient(Integer modelId);

    EasyUIDataGrid selectModelList(Integer page, Integer rows, String sort, String order, String pModelName);

    Result<String> saveModel(String playFlag, SrhModel model);

    EasyUIDataGrid executeSelect(Integer page, Integer rows, String sort, String order, Integer modelId, HashMap<String, String> params);

    Integer executeCount(Integer modelId, HashMap<String, String> params);
}
