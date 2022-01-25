package cn.bug.green.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
}