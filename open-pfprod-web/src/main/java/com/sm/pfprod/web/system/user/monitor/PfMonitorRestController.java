package com.sm.pfprod.web.system.user.monitor;

import com.sm.pfprod.web.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: PfMonitorRestController
 * @Description: 系统监控接口
 * @Author yangtongbin
 * @Date 2017/10/9 09:44
 */
@Controller
@RequestMapping(value = "/monitor")
public class PfMonitorRestController extends BaseController {

    @RequestMapping("/server/page")
    public String page() {
        return "pages/monitor/serverInfo";
    }
}
