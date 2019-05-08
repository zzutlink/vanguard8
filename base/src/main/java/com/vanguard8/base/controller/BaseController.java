package com.vanguard8.base.controller;

import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import com.vanguard8.base.service.BaseService;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/base")
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private BaseService baseService;

    @RequestMapping("/base")
    public String base(Integer bsId, Model model) {
        logger.debug(bsId.toString());
        List<BaseDetail> details = baseService.getBaseDetail(bsId);

        Integer showHeight = 140;
        Integer showWidth = 0;
        for (BaseDetail detail : details) {
            if (detail.getShowWidth() > showWidth) {
                showWidth = detail.getShowWidth();
            }
            if (detail.getEditFlag() == 1) {
                showHeight = showHeight + detail.getShowHeight();
            }
        }
        model.addAttribute("bsId", bsId);
        model.addAttribute("details", details);
        model.addAttribute("showHeight", showHeight);
        model.addAttribute("showWidth", showWidth + 180);
        return "/base/base";
    }

    @RequestMapping("/mana")
    public String mana() {
        return "/base/mana";
    }

    @RequestMapping("/getBaseMain")
    @ResponseBody
    public EasyUIDataGrid getBaseMain(Integer page, Integer rows, @RequestParam(defaultValue = "") String sort,
                                      @RequestParam(defaultValue = "") String order,
                                      @RequestParam(defaultValue = "") String bsName,
                                      @RequestParam(defaultValue = "") String tableName) {
        BaseMain main = new BaseMain();
        main.setBsName(bsName);
        main.setTableName(tableName);
        return baseService.getAllBaseMain(page, rows, sort, order, main);
    }

    @RequestMapping("/saveBaseMain")
    @ResponseBody
    public Result<String> saveBaseMain(String playFlag, String bsId, String bsName, String tableName, String keyField, String keyFieldAutoCreate,
                                       String keyFieldLength, String sortField, String checkUseTable, String checkUseField, String fieldStr,
                                       String tableStr, String conditionStr){
        Result<String> r = null;
        BaseMain main = new BaseMain();
        if(!playFlag.equals("1")){
            main.setBsId(Integer.valueOf(bsId));
        }
        if(playFlag.equals("1")||playFlag.equals("2")) {
            main.setBsName(bsName);
            main.setTableName(tableName);
            main.setKeyField(keyField);
            if (keyFieldAutoCreate == null) {
                keyFieldAutoCreate = "0";
            }
            main.setKeyFieldAutoCreate(Integer.valueOf(keyFieldAutoCreate));
            main.setKeyFieldLength(Integer.valueOf(keyFieldLength));
            main.setSortField(sortField);
            main.setCheckUseTable(checkUseTable);
            main.setCheckUseField(checkUseField);
            main.setFieldStr(fieldStr);
            main.setTableStr(tableStr);
            main.setConditionStr(conditionStr);
        }
        r = baseService.saveBaseMain(playFlag,main);
        return r;
    }

    @RequestMapping("/getBaseDetails")
    @ResponseBody
    public EasyUIDataGrid getBaseDetails(String bsId){
        EasyUIDataGrid grid = new EasyUIDataGrid();
        if(bsId!=null)
        {
            List<BaseDetail> details = baseService.getBaseDetail(Integer.valueOf(bsId));
            grid.setRows(details);
        }
        return grid;
    }


    @RequestMapping("/saveBaseDetail")
    @ResponseBody
    public Result<String> saveBaseDetail(Integer detailPlayFlag, @RequestParam(defaultValue = "0") String detailId, Integer pBsId, String fieldCode,
                                         String fieldName, Integer fieldType, @RequestParam(defaultValue = "0") String showFlag, Integer showWidth,
                                         Integer showHeight, @RequestParam(defaultValue = "0") String searchFlag, @RequestParam(defaultValue = "0") String editFlag,
                                         @RequestParam(defaultValue = "0") String readonlyFlag,@RequestParam(defaultValue = "0")  String repeatFlag,
                                         @RequestParam(defaultValue = "0") String nullFlag, @RequestParam(defaultValue = "0") String refFlag, String refString,
                                         String refIdStr, String refTextStr, String defaultValue){
        Result<String> r = null;
        BaseDetail detail = new BaseDetail();
        if(!detailPlayFlag.equals("1")){
            detail.setDetailId(Integer.valueOf(detailId));
        }
        detail.setBsId(pBsId);
        detail.setFieldCode(fieldCode);
        detail.setFieldName(fieldName);
        detail.setFieldType(fieldType);
        detail.setShowFlag(Integer.valueOf(showFlag));
        detail.setShowWidth(showWidth);
        detail.setShowHeight(showHeight);
        detail.setSearchFlag(Integer.valueOf(searchFlag));
        detail.setEditFlag(Integer.valueOf(editFlag));
        detail.setReadonlyFlag(Integer.valueOf(readonlyFlag));
        detail.setRepeatFlag(Integer.valueOf(repeatFlag));
        detail.setNullFlag(Integer.valueOf(nullFlag));
        detail.setRefFlag(Integer.valueOf(refFlag));
        detail.setRefString(refString);
        detail.setRefIdStr(refIdStr);
        detail.setRefTextStr(refTextStr);
        detail.setDefaultValue(defaultValue);

        r = baseService.saveBaseDetail(detailPlayFlag,detail);
        return r;
    }

    @RequestMapping("/getList")
    @ResponseBody
    public EasyUIDataGrid getList(Integer page, Integer rows, String sort, String order, Integer bsId, String paramName, String paramValue) {
        EasyUIDataGrid dg = new EasyUIDataGrid();
        List<Map<String, Object>> data = baseService.executeSelect(page, rows, sort, order, bsId, paramName, paramValue);
        Integer total = baseService.executeCount(bsId, paramName, paramValue);
        dg.setTotal(total);
        dg.setRows(data);
        return dg;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(HttpServletRequest request) {
        Result<String> r;

        String playFlag = request.getParameter("PlayFlag");
        Integer bsId = Integer.parseInt(request.getParameter("bsId"));

        HashMap<String, String> maps = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.equals("PlayFlag") || name.equals("bsId")) {
                continue;
            }
            //参数名是一个组合字符串 例如 0#userName，前面0#代表fieldType，0数值1文本，以区分拼装sql是否加引号'
            maps.put(name, request.getParameter(name));
            logger.debug(name);
            logger.debug(request.getParameter(name));
        }
        r = baseService.save(bsId, playFlag, maps);

        return r;
    }
}
