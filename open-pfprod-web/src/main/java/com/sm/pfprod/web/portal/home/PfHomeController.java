package com.sm.pfprod.web.portal.home;

import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: PfHomeController
 * @Description: 主页
 * @Author yangtongbin
 * @Date 2017/9/4 15:20
 */
@Controller
public class PfHomeController extends BaseController {

    @Resource
    private PfMenuService pfMenuService;

    /**
     * 网站名称
     */
    @Value("${website.name}")
    private String websiteName;

    /**
     * 版权信息
     */
    @Value("${website.copyright}")
    private String websiteCopyright;

    @RequestMapping("/index")
    public String index(Model model) {
        boolean isSuper = SecurityContext.hasRole("ROLE_SUPER");
        List<PfMenuVo> menuVos = pfMenuService.listMyMenus(isSuper, CurrentUserUtils.getCurrentUserId());
        Map<String, List<PfMenuVo>> menus = menuVos.stream().collect(Collectors.groupingBy(PfMenuVo::getPosition));
        model.addAttribute("topMenus", menus.get("top"));
        model.addAttribute("leftMenus", menus.get("left"));
        model.addAttribute("websiteName", websiteName);
        model.addAttribute("websiteCopyright", websiteCopyright);

        model.addAttribute("username", CurrentUserUtils.getCurrentUser().getUsername());
        return "/home/index";
    }

    @RequestMapping("/main")
    public String main() {
        return "/pages/main";
    }
}
