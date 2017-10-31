package com.sm.pfprod.web.portal.user.login;

import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.facade.role.PfRoleFacade;
import com.sm.pfprod.facade.user.PfUserFacade;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import org.apache.commons.lang3.StringUtils;
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
    private PfUserFacade pfUserFacade;

    @Resource
    private PfRoleFacade pfRoleFacade;

    @Resource(name = "rsaKeyPairQueue")
    private RsaKeyPairQueue rsaKeyPairQueue;

    /**
     * rsa公钥常量变量名
     */
    private static final String PUBLIC_KEY = "publicKey";

    @RequestMapping("/page")
    public String page() {
        return "pages/user/user";
    }

    @RequestMapping("/modifyPass")
    public String modifyPass(Model model, HttpServletRequest request) {
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        return "pages/user/modifyPass";
    }

    @RequestMapping("/form")
    public String form(String formType, Long userId, Model model, HttpServletRequest request) {
        model.addAttribute("formType", formType);
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());

        if (StringUtils.equals(formType, "edit")) {
            model.addAttribute("roles", pfRoleFacade.listUserRole(userId));
        } else {
            model.addAttribute("roles", pfRoleFacade.list());
        }
        return "pages/user/userForm";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(Model model, HttpServletRequest request) {
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        return "pages/user/passReset";
    }

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
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listUsers(PfUserDto dto) {
        return pfUserFacade.listUsers(dto);
    }
}
