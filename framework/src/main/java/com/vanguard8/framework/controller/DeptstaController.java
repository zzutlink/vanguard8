package com.vanguard8.framework.controller;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.common.TreeNode;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.service.DeptstaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/deptsta")
public class DeptstaController {

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
    public Result<Integer> saveDeptsta(String playFlag, Integer dsId, String dsName){
        Result<Integer> r;
        try {
            Integer i = deptstaService.saveDeptsta(playFlag, dsId, dsName);
            r = ResultGenerator.genSuccessResult(i);

        }catch(DataAccessException e){
            r = ResultGenerator.genFailResult("写入数据库时出现异常，请重试！");
        }

        return r;
    }
}
