package cn.bug.green.admin.controller.dingtalk;

import cn.bug.green.common.constant.Constants;
import cn.bug.green.common.core.domain.AjaxResult;
import cn.bug.green.dingtalk.service.DDService;
import cn.bug.green.framework.web.service.SysLoginService;
import cn.bug.green.system.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * description
 *
 * @author coding-bug
 * date 2022/2/28 22:27
 */
@RestController
public class DDController {
    private final ISysUserService userService;
    private final SysLoginService loginService;

    private final DDService ddService;

    public DDController(DDService ddService, SysLoginService loginService, ISysUserService userService) {
        this.ddService = ddService;
        this.loginService = loginService;
        this.userService = userService;
    }

    /**
     * @param params
     * @return ServiceResult
     */
    @ResponseBody
    @RequestMapping(value = "/dingLogin", method = RequestMethod.POST)
    public AjaxResult dingLogin(@RequestBody Map<String, Object> params) {
        AjaxResult ajax = AjaxResult.success();
        String token = ddService.dingLogin(params);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

}
