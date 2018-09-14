package com.sm.pfprod.web.portal.monitor;

import com.sm.pfprod.facade.monitor.PfMonitorFacade;
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
    private PfMonitorFacade pfMonitorFacade;

    @PreAuthorize("hasAnyRole('ROLE_SYS_MONITOR','ROLE_SUPER')")
    @RequestMapping("/server/page")
    public String page(HttpServletRequest request, Model model) {
        request.setAttribute("math", new MathTool());
        request.setAttribute("number", new NumberTool());
        model.addAttribute("serverInfo", pfMonitorFacade.selectServerInfo());
        return "pages/monitor/serverInfo";
    }
}
