package cn.bug.green.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author coding-bug
 * @description start
 * @createDate 2022-01-04 10:18
 */
@EnableConfigurationProperties
@ComponentScan(basePackages = {"cn.bug.green.**"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GreenApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  success start  ლ(´ڡ`ლ)ﾞ  \n" +
                "\n" +
                "               _ _                    _                 \n" +
                "              | (_)                  | |                \n" +
                "  ___ ___   __| |_ _ __   __ _ ______| |__  _   _  __ _ \n" +
                " / __/ _ \\ / _` | | '_ \\ / _` |______| '_ \\| | | |/ _` |\n" +
                "| (_| (_) | (_| | | | | | (_| |      | |_) | |_| | (_| |\n" +
                " \\___\\___/ \\__,_|_|_| |_|\\__, |      |_.__/ \\__,_|\\__, |\n" +
                "                          __/ |                    __/ |\n" +
                "                         |___/                    |___/ \n");
    }
}
