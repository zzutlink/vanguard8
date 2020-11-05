package com.vanguard8.framework.service;


import com.vanguard8.common.Result;
import com.vanguard8.framework.entity.Action;
import com.vanguard8.framework.entity.Function;

import java.util.List;

public interface FunctionService {
    List<Function> getFunctions(Integer dsId);

    List<Function> getLevelFunctions(Integer funcId);

    Result<String> saveFunction(String playFlag, Function function);

    List<Action> getFuncActions(Integer funcId);

    Result<String> saveAction(String playFlag, Action action);
}
