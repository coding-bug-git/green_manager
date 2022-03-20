package cn.bug.green.test;

import cn.bug.green.common.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * description
 *
 * @author coding-bug
 * date 2022/1/19 15:42
 */

public class NoSpringTest {
    @Test
    void passwordTest() {
        String password = "$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println("encode = " + encode);
        boolean matches = encoder.matches("admin123", password);
        System.out.println("matches = " + matches);
    }

    @Test
    void regTest() {
        List<String> permissions = List.of("a:b:c", "sys:*:*");

        String menuPerm = "sys:bi:list";

        String[] menuPerms = menuPerm.split(";");
        List<String> tmpLi;
        for (String perm : permissions) {
            System.out.println("perm = " + perm);

            for (String s : menuPerms) {
                if (!s.equalsIgnoreCase("*")) {
                    if (s.equalsIgnoreCase(perm.trim())) {

                    }
                }
            }
        }

    }

    @Test
    void myFUn() {
        System.out.println("StringUtils.capitalize(\"abc\") = " + StringUtils.capitalize("abc"));
    }


}