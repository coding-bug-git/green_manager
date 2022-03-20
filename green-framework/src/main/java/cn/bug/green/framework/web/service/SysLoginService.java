package cn.bug.green.framework.web.service;


import cn.bug.green.common.constant.Constants;
import cn.bug.green.common.core.domain.entity.SysUser;
import cn.bug.green.common.core.domain.model.LoginUser;
import cn.bug.green.common.core.redis.RedisCache;
import cn.bug.green.common.exception.ServiceException;
import cn.bug.green.common.exception.user.CaptchaException;
import cn.bug.green.common.exception.user.CaptchaExpireException;
import cn.bug.green.common.exception.user.UserPasswordNotMatchException;
import cn.bug.green.common.utils.DateUtils;
import cn.bug.green.common.utils.MessageUtils;
import cn.bug.green.common.utils.ServletUtils;
import cn.bug.green.common.utils.ip.IpUtils;
import cn.bug.green.framework.manager.AsyncManager;
import cn.bug.green.framework.manager.factory.AsyncFactory;
import cn.bug.green.system.service.ISysConfigService;
import cn.bug.green.system.service.ISysUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author coding-bug
 */
@Component
public class SysLoginService {
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final RedisCache redisCache;

    private final ISysUserService userService;

    private final ISysConfigService configService;

    public SysLoginService(TokenService tokenService, AuthenticationManager authenticationManager, RedisCache redisCache, ISysUserService userService, ISysConfigService configService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.redisCache = redisCache;
        this.userService = userService;
        this.configService = configService;
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        // boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        // if (captchaOnOff) {
        //     validateCaptcha(username, code, uuid);
        // }
        // 用户验证
        Authentication authentication;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }


    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
