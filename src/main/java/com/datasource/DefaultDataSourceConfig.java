package com.datasource;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
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
 
    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource) throws Exception 
    {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(mybatisConfigLocation));
        return sessionFactory.getObject();
    }

	@Bean(name = "defaultSqlSessionTemplate")
	public SqlSessionTemplate defaultSqlSessionTemplate(@Qualifier("defaultSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception 
	{
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name = "defaultTransactionManager")
	public DataSourceTransactionManager defaultTransactionManager(@Qualifier("defaultDataSource") DataSource defaultDataSource) throws SQLException 
	{
		return new DataSourceTransactionManager(defaultDataSource);
	}
	
	@Bean(name = "transactionInterceptor")
	public TransactionInterceptor transactionInterceptor(@Qualifier("defaultTransactionManager") DataSourceTransactionManager defaultTransactionManager) throws Throwable
	{
		Properties prop = new Properties();
		prop.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
		prop.setProperty("del*", "PROPAGATION_REQUIRED,-Exception");
		prop.setProperty("upd*", "PROPAGATION_REQUIRED,-Exception");
		prop.setProperty("get*", "PROPAGATION_NEVER,readOnly");
		prop.setProperty("query*", "PROPAGATION_NEVER,readOnly");
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(defaultTransactionManager);
		ti.setTransactionAttributes(prop);
		return ti;
	}

	@Bean(name = "beanNameAutoProxyCreator")
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() throws Throwable
	{
		BeanNameAutoProxyCreator bpc = new BeanNameAutoProxyCreator();
		bpc.setProxyTargetClass(true);
		bpc.setBeanNames("*Service");
		bpc.setInterceptorNames("transactionInterceptor");
		return bpc;
	}
}