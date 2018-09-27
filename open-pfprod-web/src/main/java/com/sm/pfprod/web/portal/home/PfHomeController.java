package com.sm.pfprod.web.portal.home;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.home.PfHomeDto;
import com.sm.pfprod.model.vo.home.PfHomeVo;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.service.home.PfHomeService;
import com.sm.pfprod.service.user.menu.PfMenuService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Resource
    private PfHomeService pfHomeService;

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

    @PreAuthorize("isAnonymous() || isAuthenticated()")
    @RequestMapping("/index")
    public String index(Model model) {
        PfHomeDto homeDto = new PfHomeDto();
        homeDto.setSuper(SecurityContext.hasRole("ROLE_SUPER") ? true : false);
        homeDto.setAnonymousUser(SecurityContext.isAnonymousUser() ? true : false);
        homeDto.setUserId(SecurityContext.isAnonymousUser() ? null : CurrentUserUtils.getCurrentUserId());
        homeDto.setIdOrg(SecurityContext.isAnonymousUser() ? null : CurrentUserUtils.getCurrentUser().getIdOrg());

        PfHomeVo pfHomeVo = pfHomeService.selectHomeInfo(homeDto);
        if (pfHomeVo == null) {
            pfHomeVo = new PfHomeVo();
        }
        pfHomeVo.setUsername(SecurityContext.isAnonymousUser() ?
                "匿名用户" : CurrentUserUtils.getCurrentUser().getUsername());
        pfHomeVo.setWebsiteName(websiteName);
        pfHomeVo.setWebsiteCopyright(websiteCopyright);
        model.addAttribute("homeInfo", pfHomeVo);
        return "/home/index";
    }

    @RequestMapping("/main")
    public String main() {
        return "/pages/main";
    }
}
