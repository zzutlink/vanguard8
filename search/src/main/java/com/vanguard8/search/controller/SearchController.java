package com.vanguard8.search.controller;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.search.entity.Condition;
import com.vanguard8.search.entity.Field;
import com.vanguard8.search.entity.SrhModel;
import com.vanguard8.search.service.ConditionService;
import com.vanguard8.search.service.FieldService;
import com.vanguard8.search.service.ModelService;
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
@RequestMapping("/search")
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ConditionService conditionService;

    private String sepStr1 = "@@@";
    private String sepStr2 = "###";

    @RequestMapping("/search")
    public String search(Integer modelId, Model model) {
        SrhModel m = modelService.getModelForClient(modelId);

//        //显示为查询条件的内容
//        string CondStr = "";
//        CondField[] CondFields = searchDal.GetCondFields(ModelID);
//        int tdCount = 0;
//        foreach (CondField cf in CondFields)
//        {
//            tdCount += 1;
//            if (tdCount == 1) { CondStr += "<tr>"; }
//            CondStr += "<td>" + cf.FieldName + "</td>";
//            if (cf.FTypeID == "0")
//            {
//                CondStr += "<td><input id='" + cf.FieldID + "' name='" + cf.FieldID + "' style='width:" + cf.ShowWidth.ToString() + "px' /></td>";
//                SubmitParamStr += cf.FieldID + ":$('#" + cf.FieldID + "').val(),";
//            }
//            else if (cf.FTypeID == "1")
//            {
//                CondStr += "<td><input id='" + cf.FieldID + "' name='" + cf.FieldID + "' class='easyui-combobox' style='width:" + cf.ShowWidth.ToString()
//                        + "px' data-options=\"url:'/Search/GetQuote?FieldID=" + cf.FieldID + "',valueField:'" + cf.IDStr + "',textField:'" + cf.TextStr + "',editable:false,"
//                        + "onLoadSuccess: function () {var data = $('#" + cf.FieldID + "').combobox('getData');if (data.length > 0) {$('#" + cf.FieldID + "').combobox('select',data[0]." + cf.IDStr + "); }}\" /></td>";
//                SubmitParamStr += cf.FieldID + ":$('#" + cf.FieldID + "').combobox('getValue'),";
//            }
//            else if (cf.FTypeID == "2")
//            {
//                CondStr += "<td><input id='" + cf.FieldID + "' name='" + cf.FieldID + "' class='easyui-datebox' style='width:" + cf.ShowWidth.ToString() + "px' /></td>";
//                InitValueStr += "$('#" + cf.FieldID + "').datebox('setValue',GetCurrDate());";
//                SubmitParamStr += cf.FieldID + ":$('#" + cf.FieldID + "').datebox('getValue'),";
//            }
//            if (tdCount == 3)
//            {
//                tdCount=0;
//                CondStr += "</tr>";
//            }
//        }
//        if (tdCount == 1)
//        {
//            CondStr += "<td></td><td></td><td></td><td></td></tr>";
//        }
//        else if (tdCount == 2)
//        {
//            CondStr += "<td></td><td></td></tr>";
//        }
//
//        if ( SubmitParamStr.Length > 0)
//        {
//            SubmitParamStr.Remove(SubmitParamStr.Length - 1, 1);
//        }
//        //通过ViewBag传递到View
//        ViewBag.ModelID = ModelID;
//        ViewBag.InitValueStr = InitValueStr;
//        ViewBag.SubmitParamStr = SubmitParamStr;
//        ViewBag.ExportStr = ExportStr;
//        ViewBag.GridSetting = GridSetting;
//        ViewBag.GridFields = "[[" + GridFields + "]]";
//        ViewBag.CondStr = CondStr;
//        return View();
        List<Condition> conditions = conditionService.selectByModelId(modelId);
        List<Field> fields = fieldService.selectByModelId(modelId);

        model.addAttribute("model", m);
        model.addAttribute("showFields", fields);
        model.addAttribute("conditions", conditions);
        model.addAttribute("sepStr1",sepStr1);
        model.addAttribute("sepStr2",sepStr2);
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
    public Result<String> saveModel(String playFlag, String modelId, String modelName, String sqlTypeId,
                                    String sortField, @RequestParam(defaultValue = "") String pagination,
                                    @RequestParam(defaultValue = "") String exportFlag,
                                    @RequestParam(defaultValue = "") String printFlag,
                                    String sqlStr) {
        Result<String> r = null;
        SrhModel model = new SrhModel();
        if (!playFlag.equals("1")) {
            model.setModelId(Integer.valueOf(modelId));
        }
        if (playFlag.equals("1") || playFlag.equals("2")) {
            model.setModelName(modelName);
            model.setSqlTypeId(Integer.valueOf(sqlTypeId));
            model.setSortField(sortField);
            model.setPagination(pagination.equals("1"));
            model.setExportFlag(exportFlag.equals("1"));
            model.setPrintFlag(printFlag.equals("1"));
            model.setSqlStr(sqlStr);
        }

        r = modelService.saveModel(playFlag, model);
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
                                    Byte fFooterTypeId, String fFooterStr, String fAlign) {
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
            field.setAlign(fAlign);
        }

        r = fieldService.saveField(fPlayFlag, field);
        return r;
    }

    @RequestMapping("/getList")
    @ResponseBody
    public EasyUIDataGrid getList(Integer page, Integer rows, @RequestParam (defaultValue = "") String sort,
                                  @RequestParam (defaultValue = "") String order,
                                  @RequestParam (defaultValue = "") String modelId, String paramStr){
        EasyUIDataGrid dg = new EasyUIDataGrid();
        if(modelId.equals("")){
            dg.setRows(new ArrayList<>());
            dg.setTotal(0);
        }
        else{
            HashMap<String,String> params = new HashMap<String,String>();

            String[] p1 = paramStr.split(sepStr2);

            for(int i=0;i<p1.length;i++){
                String[] p2= p1[i].split(sepStr1);
                String value = "";
                if(p2.length>1){
                    value=p2[1];
                }
                params.put(p2[0],value);
            }

            dg = modelService.executeSelect(page, rows, sort, order, Integer.valueOf(modelId), params);
        }
        return dg;
    }
}
