package com.vanguard8.framework.service.impl;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.common.SessionUser;
import com.vanguard8.framework.dao.DeptstaDao;
import com.vanguard8.framework.dao.FunctionDao;
import com.vanguard8.framework.dao.LogDao;
import com.vanguard8.framework.dao.UserDao;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.entity.StaFunction;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.DeptstaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class DeptstaServiceImpl implements DeptstaService {
    @Autowired
    private DeptstaDao deptstaDao;

    @Autowired
    private LogDao logDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FunctionDao functionDao;

    @Override
    public List<Deptsta> getNextLevel(Integer dsId) {
        return deptstaDao.selectNextLevel(dsId);
    }

    //    { value: '0', text: '新增同级别部门' },
    //    { value: '1', text: '新增下一级部门' },
    //    { value: '2', text: '新增同级别岗位' },
    //    { value: '3', text: '新增下一级岗位' },
    //    { value: '4', text: '编辑' },
    //    { value: '5', text: '删除' },
    //    { value: '6', text: '新增同级复用职位' }
    //成功则返回其父级节点的dsId，失败返回0
    @Transactional
    public Result<Integer> saveDeptsta(String playFlag, Integer dsId, String dsName, SessionUser user) throws DataAccessException {
        Result<Integer> r;
        //先获取选定的这个节点的数据，然后生成新节点的相关数据
        Deptsta d = deptstaDao.selectByPrimaryKey(dsId);
        if (d == null) {
            r = ResultGenerator.genFailResult("当前参考节点数据异常！");
            return r;
        }

        //获取父节点信息
        Deptsta parent = getParent(d);
        Deptsta logDeptsta = d;

        String dsTotalName = ""; //部门岗位完整名称
        Integer parentDsId = 0;  //上一级节点的dsId
        Integer i = 0;  //Update与Delete操作影响的行数

        if ("4".equals(playFlag)) { //修改
            Deptsta modiDeptsta = new Deptsta();
            modiDeptsta.setDsId(d.getDsId());
            modiDeptsta.setDsName(dsName);

            if (parent == null) {
                dsTotalName = dsName;
            } else {
                parentDsId = parent.getDsId();
                dsTotalName = parent.getDsTotalName().concat(".").concat(dsName);
            }
            modiDeptsta.setDsTotalName(dsTotalName);
            deptstaDao.updateByPrimaryKeySelective(modiDeptsta);
        } else if ("5".equals(playFlag)) {  //删除
            //先检查是否有下级组织岗位，或是否有下属人员，有则不允许删除
            List<Deptsta> childs = deptstaDao.selectNextLevel(d.getDsId());
            if (childs.size() > 0) {
                r = ResultGenerator.genFailResult("该节点存在下级节点，不允许删除！");
                return r;
            }
            List<User> users = userDao.selectByDsId(d.getDsId());
            if (users.size() > 0) {
                r = ResultGenerator.genFailResult("该节点存在下属人员，不允许删除！");
                return r;
            }
            deptstaDao.deleteByPrimaryKey(dsId);

            //如果是岗位，则要删除岗位对应的功能权限和业务范围权限

            /////////////////////////////////////////////////
            if (parent != null) {
                parentDsId = parent.getDsId();
            }
        } else {
            //dsFlag=0部门 1岗位
            Byte dsFlag = new Byte("0");
            if (playFlag.equals("2") || playFlag.equals("3") || playFlag.equals("6")) {
                dsFlag = new Byte("1");
            }
            //dsFlag=0部门 1岗位
            Byte lockFlag = new Byte("0");
            if (playFlag.equals("6")) {
                lockFlag = new Byte("2");
            }

            //dsCode 同级别，则位数相同，流水+1
            //小一级别，则位数+3，最大流水+1
            String dsCode = d.getDsCode();
            String code1 = "";
            String code2 = "";
            //新增同级别，则与当前节点具有相同的parent
            if (playFlag.equals("0") || playFlag.equals("2") || playFlag.equals("6")) {
                if (parent != null) {
                    code1 = parent.getDsCode();
                    dsTotalName = parent.getDsTotalName();
                    parentDsId = parent.getDsId();
                }
            } else {  //新增下一级别
                code1 = dsCode;
                dsTotalName = d.getDsTotalName();
                parentDsId = d.getDsId();
            }
            List<String> dsCodes = deptstaDao.selectMaxCode(code1);
            if (dsCodes.size() == 0) {
                code2 = "001";
            } else if (dsCodes.size() == 1) {
                dsCode = dsCodes.get(0);
                code2 = String.valueOf(Integer.parseInt(dsCode.substring(dsCode.length() - 3, dsCode.length())) + 1001).substring(1, 4);
            }

            dsTotalName = dsTotalName.equals("") ? dsName : dsTotalName.concat(".").concat(dsName);

            Deptsta deptsta = new Deptsta();
            deptsta.setDsCode(code1.concat(code2));
            deptsta.setDsName(dsName);
            deptsta.setDsTotalName(dsTotalName);
            deptsta.setDsFlag(dsFlag);
            deptsta.setLockFlag(lockFlag);

            i = deptstaDao.insert(deptsta);
            logDeptsta = deptsta;
        }
        //记录操作日志
        logDao.saveLog(user.getUserId(), user.getUserName(), "sys_deptsta:".concat(playFlag), logDeptsta.toString());
        r = ResultGenerator.genSuccessResult(parentDsId);
        return r;
    }

    @Override
    public List<Function> getFuncsWithRight(Integer dsId) {
        return functionDao.selectFuncsWithRight(dsId);
    }

    @Override
    @Transactional
    public Result<String> saveStatFunc(Integer dsId, String funcStr) throws DataAccessException {
        Result<String> r = ResultGenerator.genSuccessResult();
        //先删除掉原有的权限，然后按最新的逐一添加
        functionDao.deleteStatFunc(dsId);
        String[] funcIds = funcStr.split("#");
        for (int i = 0; i < funcIds.length; i++) {
            String funcId = funcIds[i];
            StaFunction record = new StaFunction();
            record.setDsId(dsId);
            record.setFuncId(funcId);
            functionDao.insertStatFunc(record);
        }
        return r;
    }

    @Override
    public boolean checkActionRight(Integer dsId, String url) {
        return deptstaDao.checkDsAction(dsId, url) > 0;
    }

    //根据当前节点的dsId获取其父节点的信息
    private Deptsta getParent(@NotNull Deptsta d) {
        Deptsta parent = null;
        String dsCode = d.getDsCode();
        String parentDsCode = "";
        if (dsCode.length() > 3) {
            parentDsCode = dsCode.substring(0, dsCode.length() - 3);
        }
        List<Deptsta> p = deptstaDao.selectByDsCode(parentDsCode);
        if (p.size() == 1) {
            parent = p.get(0);
        }
        return parent;
    }
}
