package com.xyl.fast.mapper;

import com.xyl.fast.entity.User;
import com.xyl.fast.utils.MybatisTestHelper;
import org.junit.jupiter.api.Test;

/**
 * @author xiangyanlin
 * @date 2021/9/27
 */
class UserMapperTest {


    @Test
    public void testSelectByPrimaryKey() {
        UserMapper mapper = MybatisTestHelper.getMapper(UserMapper.class);
        User user = mapper.selectByPrimaryKey(1);
        System.out.println(user);
    }

}