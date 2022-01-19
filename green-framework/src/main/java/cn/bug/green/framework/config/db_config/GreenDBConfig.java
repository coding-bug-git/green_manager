package cn.bug.green.framework.config.db_config;

import cn.bug.green.common.core.domain.XADataSourceProperties;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Description
 *
 * @author by bug
 * @Date 2021/8/2
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"cn.bug.green.**.mapper.green"}, sqlSessionFactoryRef = "greenDBSqlSessionFactory")
public class GreenDBConfig {

    @Bean("greenDBProperties")
    @ConfigurationProperties(prefix = "spring.datasource.green-db")
    public XADataSourceProperties dataSourceProperties(@Qualifier("mysqlDBParent") XADataSourceProperties dataSourceProperties) {
        return dataSourceProperties;
    }

    @Bean(name = "greenDataSource")
    public DataSource dataSource(@Qualifier("greenDBProperties") XADataSourceProperties dataSourceProperties) {
        // 这里datasource要使用阿里的支持XA的DruidXADataSource
        log.debug("properties => {}", dataSourceProperties);
        DruidXADataSource datasource = new DruidXADataSource();
        BeanUtils.copyProperties(dataSourceProperties, datasource);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(datasource);
        xaDataSource.setUniqueResourceName("greenDataSource");
        return xaDataSource;
    }

    @Bean("greenDBSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("greenDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setTypeAliasesPackage("cn.bug.green.**.domain");
        factoryBean.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        factoryBean.setConfiguration(configuration);
        configuration.setMapUnderscoreToCamelCase(true);//开启下划线转驼峰
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/green/**/*.xml"));
        // factoryBean.setPlugins(new Interceptor[]{
        //         new PaginationInterceptor(),
        //         new PerformanceInterceptor()
        //         //                        .setFormat(true),
        // });
        // sqlSessionFactory.setGlobalConfig(new GlobalConfig().setBanner(false));
        factoryBean.setPlugins(mybatisPlusInterceptor());
        return factoryBean.getObject();
    }

    private MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    // @Bean
    // public ConfigurationCustomizer configurationCustomizer() {
    //     return configuration -> configuration.setUseDeprecatedExecutor(false);
    // }
}
