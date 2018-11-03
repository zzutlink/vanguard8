package com.vanguard8.framework.controller;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.common.SessionName;
import com.vanguard8.common.TreeNode;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.DeptstaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/deptsta")
public class DeptstaController {

    private Logger logger = LoggerFactory.getLogger(DeptstaController.class);

    @Autowired
    private DeptstaService deptstaService;

    @RequestMapping("/deptsta")
    public String deptsta(){
        return "/sysmana/deptsta";
    }

    @RequestMapping("/getDeptsta")
    @ResponseBody
    public List<TreeNode> getNextLevel(@RequestParam(defaultValue = "0") Integer id){
        List<Deptsta> deptstaList = deptstaService.getNextLevel(id);
        List<TreeNode> tree = new ArrayList<TreeNode>();
        for (int i = 0; i < deptstaList.size(); i++)
        {
            TreeNode node = new TreeNode();
            Deptsta deptsta = deptstaList.get(i);
            node.setId(deptsta.getDsId());
            node.setText(deptsta.getDsName());
            node.setDsCode(deptsta.getDsCode());
            if (deptsta.getDsFlag()==1){
                node.setState("open");
            }
            else{
                node.setState("closed");
            }
            node.setAttributes(deptsta);
            tree.add(node);
        }
        return tree;
    }

    @RequestMapping("/saveDeptsta")
    @ResponseBody
    //    { value: '0', text: '新增同级别部门' },
    //    { value: '1', text: '新增下一级部门' },
    //    { value: '2', text: '新增同级别岗位' },
    //    { value: '3', text: '新增下一级岗位' },
    //    { value: '4', text: '编辑' },
    //    { value: '5', text: '删除' },
    //    { value: '6', text: '新增同级复用职位' }
    public Result<Integer> saveDeptsta(HttpServletRequest request, String playFlag, Integer dsId, String dsName){
        Result<Integer> r;
        try {
            User user =  (User)request.getSession().getAttribute(SessionName.USER);
            r = deptstaService.saveDeptsta(playFlag, dsId, dsName, user);
        }catch(DataAccessException e){
            logger.error(e.getMessage());
            r = ResultGenerator.genFailResult("写入数据库时出现异常，可能服务器繁忙，请重试！");
        }

        return r;
    }
}
