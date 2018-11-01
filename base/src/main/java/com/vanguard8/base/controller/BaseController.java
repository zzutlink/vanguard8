package com.vanguard8.base.controller;

import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.service.BaseService;
import com.vanguard8.common.DataGridJson;
import com.vanguard8.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

        Integer showHeight = 130;
        for (BaseDetail detail : details) {
            if (detail.getEditFlag() == 1) {
                showHeight = showHeight + detail.getShowHeight();
            }
        }
        model.addAttribute("bsId", bsId);
        model.addAttribute("details", details);
        model.addAttribute("showHeight", showHeight);
        return "/base/base";
    }

    @RequestMapping("/getList")
    @ResponseBody
    public DataGridJson getList(Integer page, Integer rows, String sort, String order, Integer bsId, String paramName, String paramValue) {
        DataGridJson dg = new DataGridJson();
        List<Map<String, Object>> data = baseService.executeSelect(page, rows, sort, order, bsId, paramName, paramValue);
        Integer total = baseService.executeCount(bsId, paramName, paramValue);
        dg.setTotal(total);
        dg.setRows(data);
        return dg;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(HttpServletRequest request) {
        Result<String> r = new Result<String>();

        String playFlag = request.getParameter("PlayFlag");
        Integer bsId = Integer.parseInt(request.getParameter("bsId"));

        HashMap<String, String> maps = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.equals("PlayFlag") || name.equals("bsId")) {
                continue;
            }
            maps.put(name, request.getParameter(name));
            logger.debug(name);
            logger.debug(request.getParameter(name));
        }
        r = baseService.save(bsId, playFlag, maps);

        return r;
    }
}
