package com.xyl.fast.es;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.xyl.fast.dao.EsUserDao;
import com.xyl.fast.dao.UserDao;
import com.xyl.fast.entity.EsUser;
import com.xyl.fast.entity.Product;
import com.xyl.fast.entity.User;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiangyanlin
 * @date 2021/11/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESProductDaoTest {

    @Autowired
    private EsUserDao esUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ElasticsearchOperations operations;

    @Test
    public void test() {
        esUserDao.save(new EsUser(1015, "张飞", "男"));
    }

    //根据 id 查询
    @Test
    public void findById() {
        EsUser esUser = esUserDao.findById(1015).get();
        System.out.println(esUser);
    }


    //修改
    @Test
    public void update() {
        EsUser user = new EsUser(1015, "张飞", "男");
        user.setPassword("123456");
        esUserDao.save(user);
    }

    @Test
    public void findAll() {
        Iterable<EsUser> users = esUserDao.findAll();
        System.out.println(JSON.toJSONString(users));
    }

    //删除
    @Test
    public void delete() {
        EsUser user = new EsUser();
        user.setId(1);
        esUserDao.delete(user);
    }


    //批量新增
    @Test
    public void saveAll() {
        List<EsUser> list = new ArrayList<>(40);
        for (int i = 1; i <= 40; i++) {
            User byPrimaryKey = userDao.getByPrimaryKey(i);
            if (Objects.isNull(byPrimaryKey)) {
                continue;
            }
            EsUser user = new EsUser();
            BeanUtils.copyProperties(byPrimaryKey, user);
            list.add(user);
        }
        esUserDao.saveAll(list);
    }

    //分页查询
    @Test
    public void findByPageable() {
       //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;//当前页，第一页从 0 开始，1 表示第二页
        int pageSize = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        //分页查询
        Page<EsUser> userPage = esUserDao.findAll(pageRequest);
        for (EsUser user : userPage.getContent()) {
            System.out.println(user);
        }
    }


    /**
     * term 查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userName", "test");
        Query query = new NativeSearchQueryBuilder()
                .withQuery(termQueryBuilder)
                .build();

        SearchHits<EsUser> search = operations.search(query, EsUser.class, IndexCoordinates.of("user_index"));
        System.out.println(JSON.toJSONString(search.getSearchHits()));
    }

    /**
     * term 查询加分页
     */
    @Test
    public void termQueryByPage(){
      //设置查询分页
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("profession", "工"))
                .withPageable(PageRequest.of(0, 3))
                .build();

        SearchHits<EsUser> search = operations.search(query, EsUser.class, IndexCoordinates.of("user_index"));
        System.out.println(JSON.toJSONString(search.getSearchHits()));

      }
}
