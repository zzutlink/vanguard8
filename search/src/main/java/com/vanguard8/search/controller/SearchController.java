package com.vanguard8.search.controller;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.common.SessionName;
import com.vanguard8.common.SessionUser;
import com.vanguard8.search.entity.Chart;
import com.vanguard8.search.entity.Condition;
import com.vanguard8.search.entity.Field;
import com.vanguard8.search.entity.SrhModel;
import com.vanguard8.search.service.ChartService;
import com.vanguard8.search.service.ConditionService;
import com.vanguard8.search.service.FieldService;
import com.vanguard8.search.service.ModelService;
import com.vanguard8.util.DateUtil;
import com.vanguard8.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/search")
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private ChartService chartService;

    private String sepStr1 = "@@@";
    private String sepStr2 = "###";

    @RequestMapping("/search")
    public String search(Integer modelId, Model model) {
        SrhModel m = modelService.getModelForClient(modelId);

        List<Condition> conditions = conditionService.selectByModelId(modelId);
        List<Field> fields = fieldService.selectByModelId(modelId);
        List<Chart> charts = chartService.selectByModelId(modelId);

        model.addAttribute("model", m);
        model.addAttribute("showFields", fields);
        model.addAttribute("conditions", conditions);
        model.addAttribute("charts", charts);
        model.addAttribute("sepStr1", sepStr1);
        model.addAttribute("sepStr2", sepStr2);

        return "/search/search";
    }

    @RequestMapping("/mana")
    public String mana() {
        return "/search/mana";
    }

    @RequestMapping("/getModelList")
    @ResponseBody
    public EasyUIDataGrid getModelList(Integer page, Integer rows, @RequestParam(defaultValue = "") String sort,
                                       @RequestParam(defaultValue = "") String order, @RequestParam(defaultValue = "") String pModelName) {
        return modelService.selectModelList(page, rows, sort, order, pModelName);
    }

    @RequestMapping("/saveModel")
    @ResponseBody
    public Result<String> saveModel(String playFlag, String oModelId, String modelId, String modelName, String sqlTypeId,
                                    String sortField, @RequestParam(defaultValue = "") String pagination,
                                    @RequestParam(defaultValue = "") String exportFlag,
                                    @RequestParam(defaultValue = "") String printFlag,
                                    String fieldStr, String sqlStr, String procStr) {
        Result<String> r = null;
        SrhModel model = new SrhModel();
        Integer iModelId = 0;
        if (playFlag.equals("2")) {
            iModelId = Integer.valueOf(oModelId);
        }
        model.setModelId(Integer.valueOf(modelId));
        if (playFlag.equals("1") || playFlag.equals("2")) {
            model.setModelName(modelName);
            model.setSqlTypeId(Integer.valueOf(sqlTypeId));
            model.setSortField(sortField);
            model.setPagination(pagination.equals("1"));
            model.setExportFlag(exportFlag.equals("1"));
            model.setPrintFlag(printFlag.equals("1"));
            model.setFieldStr(fieldStr);
            model.setSqlStr(sqlStr);
            model.setProcStr(procStr);
        }

        r = modelService.saveModel(playFlag, iModelId, model);
        return r;
    }

    @RequestMapping("/getConditions")
    @ResponseBody
    public EasyUIDataGrid getConditions(@RequestParam(defaultValue = "") String modelId) {
        EasyUIDataGrid dg = new EasyUIDataGrid();
        List<Condition> list = null;
        if (!modelId.equals("")) {
            list = conditionService.selectByModelId(Integer.valueOf(modelId));
        }
        dg.setRows(list);
        return dg;
    }

    @RequestMapping("/saveCondition")
    @ResponseBody
    public Result<String> saveCondition(String cPlayFlag, String conditionId, Integer cModelId, Integer cShowOrder,
                                        String cConditionName, String cConditionCode, Byte cFieldTypeId, Integer cShowWidth,
                                        String cInitValue, @RequestParam(defaultValue = "") String cMustFlag,
                                        @RequestParam(defaultValue = "") String cRefFlag, String cRefString,
                                        String cRefIdStr, String cRefTextStr
    ) {
        Result<String> r = null;
        Condition condition = new Condition();
        if (!cPlayFlag.equals("1")) {
            condition.setConditionId(Integer.valueOf(conditionId));
        }
        if (cPlayFlag.equals("1") || cPlayFlag.equals("2")) {
            condition.setModelId(cModelId);
            condition.setOrderValue(cShowOrder);
            condition.setConditionName(cConditionName);
            condition.setConditionCode(cConditionCode);
            condition.setFieldTypeId(cFieldTypeId);
            condition.setShowWidth(cShowWidth);
            condition.setInitValue(cInitValue);
            condition.setMustFlag(cMustFlag.equals("1"));
            condition.setRefFlag(cRefFlag.equals("1"));
            condition.setRefString(cRefString);
            condition.setRefIdStr(cRefIdStr);
            condition.setRefTextStr(cRefTextStr);
        }

        r = conditionService.saveCondition(cPlayFlag, condition);
        return r;
    }

    @RequestMapping("/getFields")
    @ResponseBody
    public EasyUIDataGrid getFields(@RequestParam(defaultValue = "") String modelId) {
        EasyUIDataGrid dg = new EasyUIDataGrid();
        List<Field> list = null;
        if (!modelId.equals("")) {
            list = fieldService.selectByModelId(Integer.valueOf(modelId));
        }
        dg.setRows(list);
        return dg;
    }

    @RequestMapping("/saveField")
    @ResponseBody
    public Result<String> saveField(String fPlayFlag, String fieldId, Integer fModelId, Integer fShowOrder,
                                    String fFieldName, String fFieldCode, Byte fFieldTypeId, Integer fShowWidth,
                                    @RequestParam(defaultValue = "") String fShowFooter,
                                    Byte fFooterTypeId, String fFooterStr,
                                    @RequestParam(defaultValue = "") String fFrozenFlag,
                                    String fAlign, @RequestParam(defaultValue = "") String fIsCheckbox,
                                    String fStyleStr) {
        Result<String> r = null;
        Field field = new Field();
        if (!fPlayFlag.equals("1")) {
            field.setFieldId(Integer.valueOf(fieldId));
        }
        if (fPlayFlag.equals("1") || fPlayFlag.equals("2")) {
            field.setModelId(fModelId);
            field.setOrderValue(fShowOrder);
            field.setFieldName(fFieldName);
            field.setFieldCode(fFieldCode);
            field.setFieldTypeId(fFieldTypeId);
            field.setShowWidth(fShowWidth);
            field.setShowFooter(fShowFooter.equals("1"));
            field.setFooterTypeId(fFooterTypeId);
            field.setFooterStr(fFooterStr);
            field.setFrozenFlag(fFrozenFlag.equals("1"));
            field.setAlign(fAlign);
            field.setCheckbox(fIsCheckbox.equals("1"));
            field.setStyleStr(fStyleStr);
        }

        r = fieldService.saveField(fPlayFlag, field);
        return r;
    }

    private HashMap<String, String> splitParam(@NotNull String paramStr, Integer userId, Integer dsId) {
        HashMap<String, String> params = new HashMap<String, String>();

        String[] p1 = paramStr.split(sepStr2);

        for (int i = 0; i < p1.length; i++) {
            String[] p2 = p1[i].split(sepStr1);
            String value = "";
            if (p2.length > 1) {
                value = p2[1];
            }
            params.put(p2[0], value);
        }
        //增加两个系统内设的参数
        params.put("#userId#", userId.toString());
        params.put("#dsId#", dsId.toString());

        return params;
    }

    @RequestMapping("/getList")
    @ResponseBody
    public EasyUIDataGrid getList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "0") Integer rows,
                                  @RequestParam(defaultValue = "") String sort,
                                  @RequestParam(defaultValue = "") String order,
                                  @RequestParam(defaultValue = "") String modelId, String paramStr,
                                  HttpServletRequest request) {
        EasyUIDataGrid dg = new EasyUIDataGrid();
        if (modelId.equals("")) {
            dg.setRows(new ArrayList<>());
            dg.setTotal(0);
        } else {
            SessionUser user = (SessionUser) request.getSession().getAttribute(SessionName.USER);

            HashMap<String, String> params = splitParam(paramStr, user.getUserId(), user.getDsId());

            dg = modelService.executeSelect(page, rows, sort, order, Integer.valueOf(modelId), params);
        }
        return dg;
    }

    @RequestMapping("/exportExcel")
    @ResponseBody
    public Result<String> exportExcel(@RequestParam(defaultValue = "") String exportModelId, String paramStr,
                                      String exportModelName, HttpServletRequest request, HttpServletResponse response) {
        Result<String> r = null;
        SessionUser user = (SessionUser) request.getSession().getAttribute(SessionName.USER);
        HashMap<String, String> params = splitParam(paramStr, user.getUserId(), user.getDsId());

        //获取要导出的数据
        List<Map<String, Object>> data = modelService.executeSelectAll(Integer.valueOf(exportModelId), params);

        //获取要导出的列，并把这些列的中文名和字段Code取出来
        List<Field> fields = fieldService.selectByModelId(Integer.valueOf(exportModelId));
        LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            columns.put(field.getFieldName(), field.getFieldCode());
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.createExcel(exportModelName, columns, data, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, exportModelName.concat(DateUtil.getNowString()).concat(".xls"));
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    //发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
