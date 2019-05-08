package com.vanguard8.search.controller;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.search.entity.Condition;
import com.vanguard8.search.entity.Field;
import com.vanguard8.search.service.ConditionService;
import com.vanguard8.search.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ConditionService conditionService;

    @RequestMapping("/search")
    public String search(Integer modelId, Model model){
//        string ModelID = Request.QueryString["ModelID"];
//        //获取查询模板主信息
//        SearchModel model = searchDal.GetSearchModelDetail(ModelID);
//        string InitValueStr = "";
//        string SubmitParamStr = "";
//        string GridSetting = "";
//        string ExportStr = "";
//        if (model.Pagination)
//        {
//            GridSetting = "pagination:true,pageSize:20,pageList:[20,30,40,50],";
//        }
//        if (model.ShowFooter)
//        {
//            GridSetting += "showFooter:true,";
//        }
//
//        if (model.ExportFlag)
//        {
//            ExportStr = "<input type=\"button\" value=\"查询导出到EXCEL\" onclick=\"LoadGridData('2');\" />";
//        }
//
//        //显示到GRID的字段
//        ShowField[] ShowFields = searchDal.GetShowFields(ModelID);
//        string GridFields = "";
//        foreach (ShowField sf in ShowFields)
//        {
//            //要显示的字段
//            if (GridFields != "")
//            {
//                GridFields += ",";
//            }
//            GridFields += "{ field: \'" + sf.FieldCode + "\', title: \'" + sf.FieldName + "\', width: " + sf.ShowWidth.ToString() + ",sortable:true, align:'" + sf.Align + "' }";
//        }
//
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

        model.addAttribute("showFields",fields);
        model.addAttribute("conditions",conditions);
        return "/search/search";
    }

    @RequestMapping("/mana")
    public String mana(){
        return "/search/mana";
    }

//    @RequestMapping("/getList")
//    public EasyUIDataGrid getList(){
//        EasyUIDataGrid dg = new EasyUIDataGrid();
//        List<Map<String, Object>> data = baseService.executeSelect(page, rows, sort, order, bsId, paramName, paramValue);
//        Integer total = baseService.executeCount(bsId, paramName, paramValue);
//        dg.setTotal(total);
//        dg.setRows(data);
//        return dg;
//    }
}
