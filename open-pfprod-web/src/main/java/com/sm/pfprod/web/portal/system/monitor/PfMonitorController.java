package com.sm.pfprod.web.portal.system.monitor;

import com.sm.pfprod.service.system.monitor.PfMonitorService;
import com.sm.pfprod.web.portal.BaseController;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: PfMonitorRestController
 * @Description: 系统监控接口
 * @Author yangtongbin
 * @Date 2017/10/9 09:44
 */
@Controller
@RequestMapping(value = "/pf/p/monitor")
public class PfMonitorController extends BaseController {

    @Resource
    private PfMonitorService pfMonitorService;

    @PreAuthorize("hasAnyRole('ROLE_SYS_MONITOR','ROLE_SUPER')")
    @RequestMapping("/server/page")
    public String page(HttpServletRequest request, Model model) {
        request.setAttribute("math", new MathTool());
        request.setAttribute("number", new NumberTool());
        model.addAttribute("serverInfo", pfMonitorService.selectServerInfo());
        return "pages/monitor/serverInfo";
    }
}
