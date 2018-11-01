package com.vanguard8.framework.service.impl;

import com.vanguard8.framework.dao.DeptstaDao;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.service.DeptstaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptstaServiceImpl implements DeptstaService {
    @Autowired
    private DeptstaDao deptstaDao;

    @Override
    public List<Deptsta> getNextLevel(Integer dsId) {
        return deptstaDao.selectNextLevel(dsId);
    }

    @Override
    //    { value: '0', text: '新增同级别部门' },
    //    { value: '1', text: '新增下一级部门' },
    //    { value: '2', text: '新增同级别岗位' },
    //    { value: '3', text: '新增下一级岗位' },
    //    { value: '6', text: '新增同级复用职位' }
    public Integer saveDeptsta(String playFlag, Integer dsId, String dsName) throws DataAccessException {
        //先获取选定的这个节点的数据，然后生成新节点的相关数据
        Deptsta d = deptstaDao.selectByPrimaryKey(dsId);
        if (d == null) {
            return 0;
        }
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
        if (playFlag.equals("0") || playFlag.equals("2") || playFlag.equals("6")) {
            if (dsCode.length() > 3) {
                code1 = dsCode.substring(0, 3);
            }
        } else {
            code1 = dsCode;
        }
        List<String> dsCodes = deptstaDao.selectMaxCode(code1);
        if (dsCodes.size() == 0) {
            code2 = "001";
        } else if (dsCodes.size() == 1) {
            dsCode = dsCodes.get(0);
            code2 = String.valueOf(Integer.parseInt(dsCode.substring(dsCode.length() - 3, dsCode.length())) + 1001).substring(1, 4);
        }

        //获取上一级别的dsTotalName，然后+dsName
        String dsTotalName = "";
        Integer parentDsId = 0;
        if (code1.equals("")) {
            dsTotalName = dsName;
        } else {
            List<Deptsta> p = deptstaDao.selectByDsCode(code1);
            if (p.size() == 1) {
                parentDsId = p.get(0).getDsId();
                dsTotalName = p.get(0).getDsTotalName().concat(".").concat(dsName);
            }
        }

        Deptsta deptsta = new Deptsta();
        deptsta.setDsCode(code1.concat(code2));
        deptsta.setDsName(dsName);
        deptsta.setDsTotalName(dsTotalName);
        deptsta.setDsFlag(dsFlag);
        deptsta.setLockFlag(lockFlag);

        Integer i = deptstaDao.insert(deptsta);

        return parentDsId;
    }
}
