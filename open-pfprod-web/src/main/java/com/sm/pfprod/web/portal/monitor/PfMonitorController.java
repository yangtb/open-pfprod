package com.sm.pfprod.web.portal.monitor;

import com.sm.pfprod.web.portal.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: PfMonitorRestController
 * @Description: 系统监控接口
 * @Author yangtongbin
 * @Date 2017/10/9 09:44
 */
@Controller
@RequestMapping(value = "/pf/p/monitor")
public class PfMonitorController extends BaseController {

    @RequestMapping("/server/page")
    public String page() {
        return "pages/monitor/serverInfo";
    }
}
