package com.sm.pfprod.web.system.user.login;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.utils.rsa.RSAEncrypt;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.facade.role.PfRoleFacade;
import com.sm.pfprod.facade.user.PfUserFacade;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import com.sm.pfprod.web.system.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登陆
 */
@Controller
@RequestMapping(value = "/user")
public class PfUserRestController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfUserRestController.class);

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

    /**
     * 新增用户
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveUser(@RequestBody RegisterDto dto, HttpServletRequest request) {
        /* 参数校验 */
        Assert.isTrue(dto.getUsername() != null, "username");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), "password");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoles()), "roles");

        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        // 密码转化为明文
        String plainPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getPassword());
        dto.setPassword(plainPsw);

        return ResultObject.create("saveUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.saveUser(dto));
    }

    /**
     * 编辑用户
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateUser(@RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getUsername() != null, "username");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoles()), "roles");

        return ResultObject.create("updateUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.updateUser(dto));
    }

    /**
     * 删除用户
     *
     * @param dto 用户id集合
     * @return
     */
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject freezeUser(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto != null || CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return ResultObject.create("freezeUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.freezeUser(dto.getList()));
    }

    /**
     * 冻结用户
     *
     * @param dto 用户id集合
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delUser(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto != null || CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return ResultObject.create("delUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.delUser(dto.getList()));
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePsw", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updatePsw(HttpServletRequest request, @RequestBody UpdatePswDto dto) {
         /* 参数校验 */
        Assert.isTrue(dto.getOldPassword() != null, "oldPassword");
        Assert.isTrue(dto.getNewPassword() != null, "newPassword");

        dto.setUserId(CurrentUserUtils.getCurrentUserId());
        dto.setUserName(CurrentUserUtils.getCurrentUsername());

        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        // 密码转化为明文
        String plainOldPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getOldPassword());
        String plainNewPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getNewPassword());
        dto.setOldPassword(plainOldPsw);
        dto.setNewPassword(plainNewPsw);

        return ResultObject.create("updatePsw", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.updatePsw(dto));
    }

    /**
     * 密码重置
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/resetPsw", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject resetPsw(HttpServletRequest request, @RequestBody RegisterDto dto) {
         /* 参数校验 */
        Assert.isTrue(dto.getPassword() != null, "password");
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        // 密码转化为明文
        String plainPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getPassword());
        dto.setPassword(plainPsw);
        return ResultObject.create("resetPsw", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.resetPsw(dto));
    }

}
