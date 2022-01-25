package cn.bug.green.admin.controller;

import cn.bug.green.common.core.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description
 *
 * @author by bug
 * Date 2021/11/23
 */
@Controller
public class PageController extends BaseController {
    @GetMapping(value = {"/index", ""})
    public String index() {
        return redirect("/swagger-ui/");
    }

    @PreAuthorize("hasAuthority('tool:swagger:view')")
    @GetMapping(value = {"/tool/manager"})
    public String swagger() {
        return redirect("/swagger-ui/");
    }
}
