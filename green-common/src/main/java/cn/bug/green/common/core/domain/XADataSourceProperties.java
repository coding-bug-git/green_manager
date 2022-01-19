package cn.bug.green.common.core.domain;

import lombok.Data;

/**
 * Description
 *
 * @author by bug
 * @Date 2021/6/22
 */
@Data
public class XADataSourceProperties {
    private String driverClassName;
    private int initialSize;

    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private int maxEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;

    private String url;

    private String username;

    private String password;

    // private String filters;
}
