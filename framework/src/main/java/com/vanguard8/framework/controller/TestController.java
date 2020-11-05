package com.vanguard8.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanguard8.base.service.BaseService;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.DictService;
import com.vanguard8.framework.service.UserService;
import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;
import com.vanguard8.util.Html2Pdf;
import com.vanguard8.util.MD5Util;
import com.vanguard8.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private DictService dictService;

    @Autowired
    private BaseService baseService;

    @RequestMapping("/getUsers")
    public EasyUIDataGrid getUsers(){
        User user = new User();
        user.setUserName("");
        user.setLoginName("");
        return userService.getUsers(1,20,"","", user);
    }

    @RequestMapping("/getDict")
    public List<Dict> getDict(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer flag, @RequestParam(defaultValue = "") String condition){
        DictCondition c = new DictCondition();
        c.setFlag(flag);
        c.setCondition(condition);

        return dictService.getDict(c);
    }

    @RequestMapping("/md52")
    public String md5(String salt, String pwd){
        return MD5Util.MD5Encode(pwd.concat(salt));
    }

    @RequestMapping("/pdf")
    public void pdf(int width,int height){
        try {
            Html2Pdf.createPdf(width,height,"d://hero.pdf");
        }catch(Exception e)
        {}
    }

    @RequestMapping("/generateTestData")
    public void generateTestData(){
        String sql = "insert into test_orders(order_no,client_name,goods_id,order_count,goods_cost,order_price,order_time) values('#1#','#2#',#3#,#4#,#5#,#6#,'#7#')";
        Random r = new Random();

        for(int i=1;i<=1000;i++){
        Integer days = r.nextInt(90);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-days);


        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String order_time = format2.format(c.getTime());
        String order_no=format.format(c.getTime()) + String.valueOf(r.nextInt(99999)+100000).substring(1,6);

        String client_name= StringUtil.generateString(12);

        Integer goods_id=r.nextInt(3)+1;

        Integer order_count = r.nextInt(500)+1;

        Double[] costList={8888.00,4880.00,4999.00};
        Double order_cost = costList[goods_id-1];
        Double order_price=order_cost+r.nextInt(200)+100;

        String esql = sql.replace("#1#",order_no)
                .replace("#2#",client_name)
                .replace("#3#",goods_id.toString())
                .replace("#4#",order_count.toString())
                .replace("#5#",order_cost.toString())
                .replace("#6#",order_price.toString())
                .replace("#7#",order_time);

            Integer result = baseService.executeInsert(esql);
        }
    }

    @RequestMapping("/json")
    public String json()
    {
        String json = "{\"json\":{\"a\":{\"www\":\"ff\",\"rrr\":[\"v1\",\"v2\"]},\"b\":{\"www\":\"4567ttt\",\"rrr\":[\"v1\",\"v2\"]}}}";
        JSONObject ob = JSON.parseObject(json);

        return "";
    }
}
