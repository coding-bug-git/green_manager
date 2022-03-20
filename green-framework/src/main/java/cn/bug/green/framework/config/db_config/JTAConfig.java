package cn.bug.green.framework.config.db_config;


import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author coding-bug
 * date 2022/1/27 10:44
 */
@Configuration
public class JTAConfig {
    //分布式事务管理器
    // @Bean(name = "atomikosTransactionManager")
    // public TransactionManager atomikosTransactionManager() {
    //     UserTransactionManager userTransactionManager = new UserTransactionManager();
    //
    //     userTransactionManager.setForceShutdown(false);
    //     return userTransactionManager;
    // }
    //
    // @Bean(name = "txManager")
    // @DependsOn({"userTransaction", "atomikosTransactionManager"})
    // public PlatformTransactionManager transactionManager() throws Throwable {
    //     UserTransaction userTransaction = userTransaction();
    //     TransactionManager atomikosTransactionManager = atomikosTransactionManager();
    //     JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    //     jtaTransactionManager.afterPropertiesSet();
    //
    //     return jtaTransactionManager;
    // }
    //
    // @Bean(name = "userTransaction")
    // public UserTransaction userTransaction() throws Throwable {
    //     UserTransactionImp userTransactionImp = new UserTransactionImp();
    //     userTransactionImp.setTransactionTimeout(10000);
    //     return userTransactionImp;
    // }

}
