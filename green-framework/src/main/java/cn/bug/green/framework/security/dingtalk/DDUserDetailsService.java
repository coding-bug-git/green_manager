package cn.bug.green.framework.security.dingtalk;

import cn.bug.green.common.core.domain.entity.SysUser;
import cn.bug.green.common.core.domain.model.LoginUser;
import cn.bug.green.common.enums.UserStatus;
import cn.bug.green.common.exception.ServiceException;
import cn.bug.green.common.utils.StringUtils;
import cn.bug.green.framework.web.service.SysPermissionService;
import cn.bug.green.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author coding-bug
 * date 2022/3/6 9:04
 */
@Slf4j
@Component("DDUserDetailService")
public class DDUserDetailsService implements UserDetailsService {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String dingId) throws UsernameNotFoundException {
        // 根据dingId 获取用户信息
        SysUser user = userService.selectUserByDingId(dingId);
        if (StringUtils.isNull(user)) {
            log.info("登录钉钉用户：{} 不存在. 请联系管理员绑定！", dingId);
            throw new ServiceException("登录用户：" + dingId + " 不存在. 请联系管理员绑定！");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录钉钉用户：{} 已被删除.", dingId);
            throw new ServiceException("对不起，您的账号：" + dingId + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录钉钉用户：{} 已被停用.", dingId);
            throw new ServiceException("对不起，您的账号：" + dingId + " 已停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
