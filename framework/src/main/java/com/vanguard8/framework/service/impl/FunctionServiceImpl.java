package com.vanguard8.framework.service.impl;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.framework.dao.FunctionDao;
import com.vanguard8.framework.entity.Action;
import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.service.FunctionService;
import com.vanguard8.util.SequenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionDao functionDao;

    @Override
    public List<Function> getFunctions(Integer dsId) {
        List<Function> functions = functionDao.selectFunctions(dsId);
        return functions;
    }

    @Override
    public List<Function> getLevelFunctions(Integer funcId) {
        return functionDao.selectLevelFunctions(funcId);
    }

    @Override
    @Transactional
    public Result<String> saveFunction(String playFlag, Function function) {
        Result<String> r = null;
        Integer i = 0;
        String funcCode = "";
        String prefix = "";
        if (playFlag.equals("1")) {
            prefix = function.getFuncCode();
            funcCode = functionDao.selectMaxCode(prefix);
            if (funcCode == null) {
                funcCode = prefix + "001";
            } else {
                if (function.getIsLast() == 0) {
                    funcCode = String.valueOf(1001 + Integer.valueOf(funcCode)).substring(1);
                } else {
                    funcCode = prefix + String.valueOf(1001 + Integer.valueOf(funcCode.substring(4))).substring(1);
                }
            }
            function.setFuncCode(funcCode);

            i = functionDao.insertFunction(function);
        } else if (playFlag.equals("2")) {
            if (function.getIsLast() == 1) {
                Function f = functionDao.selectFunction(function.getFuncId());
                funcCode = f.getFuncCode().substring(0, 3);
                prefix = function.getFuncCode();
                if (prefix.equals(funcCode)) {
                    funcCode = f.getFuncCode();
                } else {
                    funcCode = functionDao.selectMaxCode(prefix);
                    if (funcCode == null) {
                        funcCode = "001";
                    } else {
                        funcCode = String.valueOf(1001 + Integer.valueOf(funcCode.substring(4))).substring(1);
                    }
                    funcCode = prefix + funcCode;
                }
                function.setFuncCode(funcCode);
            }
            i = functionDao.updateByPrimaryKey(function);
        } else if (playFlag.equals("3")) {
            functionDao.deleteStatFuncByFuncId(function.getFuncId());

            i = functionDao.deleteByPrimaryKey(function.getFuncId());
        }
        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("保存数据出现错误！");
        }
        return r;
    }

    @Override
    public List<Action> getFuncActions(Integer funcId) {
        return functionDao.selectFuncActions(funcId);
    }

    @Override
    public Result<String> saveAction(String playFlag, Action action) {
        Result<String> r;
        Integer i = 0;
        if(playFlag.equals("1")){
            i = functionDao.insertAction(action);
        }
        else if(playFlag.equals("2")){
            i = functionDao.updateAction(action);
        }
        else if(playFlag.equals("3")){
            i = functionDao.deleteAction(action.getfId());
        }
        if(i == 1){
            r = ResultGenerator.genSuccessResult();
        }
        else{
            r = ResultGenerator.genFailResult("保存动作失败！");
        }
        return r;
    }

    //生成一个数值格式字符串(例如 000001 )的下一个值
    //传入的值不允许为空与null，长度不能大于9位，否则返回null
    //prefix前缀，value除去prefix的目前值，length指value部分的长度
    //prefix两种情况，一是“”，二是三位字符串比如001
    private String NextSequence(String prefix, String value, Integer length) {
        String result = null;
        if (value == null) {
            result = "001";
        } else {
            result = String.valueOf(1001 + Integer.valueOf(value)).substring(1);
        }
        result = prefix + result;
        return result;
    }
}
