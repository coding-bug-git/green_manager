package cn.bug.green.dingtalk.service;

import cn.bug.green.common.core.domain.entity.SysUser;
import cn.bug.green.common.core.domain.model.LoginUser;
import cn.bug.green.dingtalk.utils.AccessTokenUtil;
import cn.bug.green.framework.web.service.SysLoginService;
import cn.bug.green.framework.web.service.SysPermissionService;
import cn.bug.green.framework.web.service.TokenService;
import cn.bug.green.system.service.ISysUserService;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author coding-bug
 * date 2022/2/28 22:34
 */
@Component
public class DDService {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private SysPermissionService permissionService;


    public Object getToken() throws Exception {
        List<String> args = List.of("111");
        com.aliyun.dingtalkoauth2_1_0.Client client = createClient();
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest()
                .setAppKey("dingpsvxqmnyfpufaffb")
                .setAppSecret("tl7PTolvP1CJCtDX--w6atE6ogyWaIDSqmQCGKFf7dpwmeWSxW163RHgBvlKVL1N");
        try {
            GetAccessTokenResponse response = client.getAccessToken(getAccessTokenRequest);
            return response;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err ????????? code ??? message ????????????????????????????????????
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err ????????? code ??? message ????????????????????????????????????
            }

        }

        return null;
    }

    /**
     * ?????? Token ???????????????Client
     *
     * @return Client
     * @throws Exception
     */
    public com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    /**
     * @param params
     * @return ServiceResult
     * 2020-11-3
     */

    public String getUserId(Map<String, Object> params) {
        // ??????access_token??????????????????????????????????????????
        String access_token = null;
        try {
            access_token = AccessTokenUtil.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ??????????????????
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(params.get("code").toString());
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, access_token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        // ???????????????????????????userId
        // ?????????userId????????????????????????????????????????????????????????????session???,??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        return response.getUserid();
    }

    public String dingLogin(Map<String, Object> params) {
        // String dingId = getUserId(params);

        String dingId = "manager6416";
        SysUser sysUser = userService.selectUserByDingId(dingId);
        loginService.recordLoginInfo(sysUser.getUserId());

        LoginUser loginUser = new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, permissionService.getMenuPermission(sysUser));
        // ??????token
        return tokenService.createToken(loginUser);
    }
}
