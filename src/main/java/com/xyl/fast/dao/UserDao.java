package com.xyl.fast.dao;

import com.xyl.fast.entity.User;
import com.xyl.fast.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiangyanlin
 * @date 2021/9/23
 */
@Component
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public User getByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }


}
