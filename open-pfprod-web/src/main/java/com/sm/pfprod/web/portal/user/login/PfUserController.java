package com.sm.pfprod.web.portal.user.login;

import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.org.PfOrgService;
import com.sm.pfprod.service.user.login.PfUserService;
import com.sm.pfprod.service.user.role.PfRoleService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登陆
 */
@Controller
@RequestMapping(value = "/pf/p/user")
public class PfUserController extends BaseController {

    @Resource
    private PfUserService pfUserService;

    @Resource
    private PfRoleService pfRoleService;

    @Resource
    private PfOrgService pfOrgService;

    @Resource(name = "rsaKeyPairQueue")
    private RsaKeyPairQueue rsaKeyPairQueue;

    @Value("${website.name}")
    private String websiteName;

    @Value("${website.copyright}")
    private String websiteCopyright;

    @Value("${website.approve}")
    private String websiteApprove;

    /**
     * rsa公钥常量变量名
     */
    private static final String PUBLIC_KEY = "publicKey";

    @RequestMapping("/register/page")
    public String registerPage(Model model, HttpServletRequest request) {
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        model.addAttribute("websiteName", websiteName);
        return "pages/user/register/register";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute("orgMap", pfOrgService.listAllOrgMap());
        return "pages/user/user";
    }

    @PreAuthorize("hasAnyRole('ROLE_MODIFY_PASS','ROLE_SUPER')")
    @RequestMapping("/modifyPass")
    public String modifyPass(Model model, HttpServletRequest request) {
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        return "pages/user/modifyPass";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Long userId, Model model, HttpServletRequest request) {
        model.addAttribute("formType", formType);
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        // 角色处理
        if (StringUtils.equals(formType, "edit")) {
            model.addAttribute("roles", pfRoleService.listUserRole(userId));
        } else {
            model.addAttribute("roles", pfRoleService.list());
        }
        // 机构处理
        model.addAttribute("allOrg", pfOrgService.listAllOrg());
        return "pages/user/userForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER_RESET_PSW','ROLE_SUPER')")
    @RequestMapping("/resetPassword")
    public String resetPassword(Model model, HttpServletRequest request) {
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        return "pages/user/passReset";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER_MG','ROLE_SUPER')")
    @RequestMapping("/userDetail")
    public String userDetail(Model model) {
        return "pages/user/userDetail";
    }


    /**
     * 获取用户列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listUsers(PfUserDto dto) {
        return pfUserService.listUsers(dto);
    }
}
