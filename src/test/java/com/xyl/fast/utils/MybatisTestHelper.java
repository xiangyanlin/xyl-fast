package com.xyl.fast.utils;

import java.io.IOException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author xiangyanlin
 * @date 2021/9/29
 */
public class MybatisTestHelper {

    private static String driver = "com.mysql.cj.jdbc.Driver";

    private static String url = "jdbc:mysql://115.29.173.51:3306/";

    private static String dbName = "rental";

    private static String username = "root";

    private static String password = "FHN11AUp%0GSX@rZ$8Ji";

    private static String mapperInterfaceLocation = "com.xyl.fast";

    private static String mapperXmlLocation = "classpath*:/mapper/*.xml";

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            sqlSessionFactory = buildSqlSessionFactory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static  <T> T getMapper(Class<T> clazz) {
        return sqlSessionFactory.getConfiguration().getMapper(clazz, sqlSessionFactory.openSession(true));
    }

    private static SqlSessionFactory buildSqlSessionFactory() throws IOException {
        Configuration mybatisConfiguration = new Configuration();

        Environment environment = new Environment.Builder("default")
                .dataSource(new UnpooledDataSource(driver, url + dbName, username, password))
                .transactionFactory(new JdbcTransactionFactory())
                .build();
        mybatisConfiguration.setEnvironment(environment);
        mybatisConfiguration.addMappers(mapperInterfaceLocation);

        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourceResolver.getResources(mapperXmlLocation);
        for (Resource resource : resources) {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(),
                    mybatisConfiguration, resource.toString(), mybatisConfiguration.getSqlFragments());
            xmlMapperBuilder.parse();
        }

        return new SqlSessionFactoryBuilder().build(mybatisConfiguration);
    }
}
