package cn.bug.green.framework.config.db_config;

import cn.bug.green.common.core.domain.XADataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description
 *
 * @author by bug
 * @Date 2021/8/2
 */
@Configuration
@EnableTransactionManagement
public class DBCommonConfig {

    @Bean("oracleDBParent")
    @ConfigurationProperties(prefix = "spring.datasource.oracledb-parent")
    public XADataSourceProperties oracleDBParent() {
        return new XADataSourceProperties();
    }

    @Bean("sqlDBParent")
    @ConfigurationProperties(prefix = "spring.datasource.sqldb-parent")
    public XADataSourceProperties sqlDBParent() {
        return new XADataSourceProperties();
    }

    @Bean("mysqlDBParent")
    @ConfigurationProperties(prefix = "spring.datasource.mysqldb-parent")
    public XADataSourceProperties mysqlDBParent() {
        return new XADataSourceProperties();
    }
}
