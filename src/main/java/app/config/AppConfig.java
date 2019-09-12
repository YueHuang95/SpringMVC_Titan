package app.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"app"})
@MapperScan("app.dao")
public class AppConfig {
    @Autowired
    private Environment env;

    // set up a logger for diagnostics
    private Logger logger = Logger.getLogger(getClass().getName());

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean
    public DataSource dataSource() {
        // create connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // set the jdbc driver class
        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        // 打印Jdbc连接信息
        logger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
        logger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        // set connection pool props

        dataSource.setInitialPoolSize(
                getIntProperty("connection.pool.initialPoolSize"));

        dataSource.setMinPoolSize(
                getIntProperty("connection.pool.minPoolSize"));

        dataSource.setMaxPoolSize(
                getIntProperty("connection.pool.maxPoolSize"));

        dataSource.setMaxIdleTime(
                getIntProperty("connection.pool.maxIdleTime"));


        return dataSource;
    }
    // need a helper method
    // read environment property and convert to int

    private int getIntProperty(String propName) {

        String propVal = env.getProperty(propName);

        // now convert to int
        int intPropVal = Integer.parseInt(propVal);

        return intPropVal;
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置MyBatis 配置文件的路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("SqlMapConfig.xml"));
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource());
        SqlSessionFactory sessionFactory = sqlSessionFactoryBean.getObject();
        return sessionFactory;

    }

}

