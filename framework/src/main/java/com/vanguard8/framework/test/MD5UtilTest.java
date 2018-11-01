package com.vanguard8.framework.test;

import com.vanguard8.base.dao.BaseDao;
import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import com.vanguard8.framework.dao.UserDao;
import com.vanguard8.framework.entity.User;
import com.vanguard8.util.MD5Util;
import com.vanguard8.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class MD5UtilTest {

    private Logger logger = LoggerFactory.getLogger(MD5UtilTest.class);

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/selectByLoginname")
    public User selectByLoginname(String loginName){
        return userDao.selectByLoginname("admin");
    }


    @RequestMapping("/md5")
    public String MD5(){
        String salt = StringUtil.generateString(8);
        String pwd = "111";
        return salt.concat("       ").concat(MD5Util.MD5Encode(pwd.concat(salt)));
    }

    @RequestMapping("/log")
    public void log(){
        logger.info("info中文");
        logger.debug("debug中文");
        logger.warn("warn中文");
        logger.error("error中文");
    }

    @RequestMapping("/basemain")
    public BaseMain getBaseMain(Integer bsId){
        BaseMain b = baseDao.getBaseMain(1);
        return b;
    }

    @RequestMapping("/basedetail")
    public List<BaseDetail> getBaseDetail(Integer bsId){
        List<BaseDetail> b = baseDao.getBaseDetail(1);
        return b;
    }
}
