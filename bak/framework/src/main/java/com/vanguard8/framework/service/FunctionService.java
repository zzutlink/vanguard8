package com.vanguard8.framework.service;

import com.vanguard8.framework.entity.Function;

import java.util.List;

public interface FunctionService {
    List<Function> getFunctions(Integer dsId);
}
