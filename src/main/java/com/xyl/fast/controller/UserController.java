package com.xyl.fast.controller;

import com.xyl.fast.dao.UserDao;
import com.xyl.fast.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiangyanlin
 * @date 2021/9/23
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/queryOne")
    @ResponseBody
    public User getUserDetail(Integer id) {
        return userDao.getByPrimaryKey(id);
    }
}
