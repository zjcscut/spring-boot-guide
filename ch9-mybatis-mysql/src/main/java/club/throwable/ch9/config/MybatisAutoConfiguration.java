package club.throwable.ch9.config;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/19 0:00
 */
@Configuration
@EnableConfigurationProperties(value = {MyBatisProperties.class})
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter(value = DataSourceAutoConfiguration.class)
@RequiredArgsConstructor
public class MybatisAutoConfiguration {

    private final MyBatisProperties myBatisProperties;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 其实核心配置就是这两项,其他TypeHandlersPackage、TypeAliasesPackage等等自行斟酌是否需要添加
        bean.setConfigLocation(new ClassPathResource(myBatisProperties.getConfigLocation()));
        bean.setMapperLocations(myBatisProperties.getMapperResourceArray());
        return bean;
    }

    /**
     * 是否模板,用于编程式事务 - 可选配置
     */
    @Bean
    @ConditionalOnMissingBean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    /**
     * 数据源事务管理器 - 可选配置
     */
    @Bean
    @ConditionalOnMissingBean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
