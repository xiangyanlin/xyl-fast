package com.xyl.fast.dao;

import com.xyl.fast.entity.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiangyanlin
 * @date 2021/11/11
 */
@Repository
public interface EsUserDao extends ElasticsearchRepository<EsUser, Integer> {


}
