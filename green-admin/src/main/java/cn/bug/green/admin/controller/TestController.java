package cn.bug.green.admin.controller;


import cn.bug.green.framework.web.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Description
 *
 * @author by bug
 * @Date 2021/11/17
 */
@Api(tags = {"测试"})
@RestController
@RequestMapping("test")
public class TestController {

    // @Autowired
    // private FactoryService factoryService;
    // @Autowired
    // private DeviceService deviceService;
    // @Autowired
    // private AreaService areaService;
    @Autowired
    TestService testService;

    @PreAuthorize("hasAuthority('system:')")
    @ApiOperation("联表视图测试")
    @GetMapping("1")
    public Object test1() {
        return testService.listFactoryInfo();
    }
}
