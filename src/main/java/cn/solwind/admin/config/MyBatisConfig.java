package cn.solwind.admin.config;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;

/**
 * @author Zln
 * @version 2018-11-23 14:48
 * 
 */

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:druid.properties")
@MapperScan("cn.solwind.admin.dao")
public class MyBatisConfig {
	
    @Value("${druid.driverClassName}")
    private String driver;

    @Value("${druid.url}")
    private String url;

    @Value("${druid.username}")
    private String username;

    @Value("${druid.password}")
    private String password;

    @Value("${druid.initialSize}")
    private int initialSize;

    @Value("${druid.minIdle}")
    private int minIdle;

    @Value("${druid.maxActive}")
    private int maxActive;

    @Value("${druid.maxWait}")
    private int maxWait;

    @Value("${druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        // 基本属性 url、user、password
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);

        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(maxWait);

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        dataSource.setValidationQuery("SELECT 'X'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        // 是否打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        
        try {
			dataSource.setFilters("wall,log4j2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        // 设置 mapper xml
        //sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));

        // 分页拦截器-begin
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        pageInterceptor.setProperties(properties);
        sessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        // 分页拦截器-end
        
        // 注意!!所有sessionFactoyBean的配置必须在getObject方法之前进行设置
        SqlSessionFactory sessionFactory = sessionFactoryBean.getObject();
        sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory;
    }
    
    
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}