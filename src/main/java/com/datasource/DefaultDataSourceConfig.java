package com.datasource;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySource(value = "classpath:datasource.properties", ignoreResourceNotFound = true)
public class DefaultDataSourceConfig 
{
    @Value("${default.datasource.url}")
    private String url;
    @Value("${default.datasource.username}")
    private String user;
    @Value("${default.datasource.password}")
    private String password;
    @Value("${default.datasource.driver-class-name}")
    private String driverClass;
    @Value("${default.datasource.filters}")
    private String filters;
    @Value("${default.datasource.initialSize}")
    private int initialSize;
    @Value("${default.datasource.maxActive}")
    private int maxActive;
    @Value("${default.datasource.minIdle}")
    private int minIdle;
    @Value("${default.datasource.maxWait}")
    private int maxWait;
    @Value("${default.datasource.mapper-location}")
    private String mapperLocation;
    @Value("${mybatis.config-location}")
    private String mybatisConfigLocation;
 
    @Bean(name = "defaultDataSource")
    @Primary
    public DataSource defaultDataSource() throws SQLException 
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setFilters(filters);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        return dataSource;
    }

//	  打开后，分布式事务JTA失效
    @Bean(name = "defaultTransactionManager")
    @Primary
    public DataSourceTransactionManager defaultTransactionManager() throws SQLException 
    {
        return new DataSourceTransactionManager(defaultDataSource());
    }
 
    @Bean(name = "defaultSqlSessionFactory")
    @Primary
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource) throws Exception 
    {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(mybatisConfigLocation));
        return sessionFactory.getObject();
    }

	@Bean(name = "defaultSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate defaultSqlSessionTemplate(@Qualifier("defaultSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception 
	{
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}