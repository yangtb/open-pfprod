package com.sm.pfprod.web.rest.user.login;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.utils.rsa.RSAEncrypt;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.dto.user.register.RegisterVcodeDto;
import com.sm.pfprod.model.dto.user.register.UserRegisterDto;
import com.sm.pfprod.service.user.login.PfUserService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.SecurityContext;
import com.sm.pfprod.web.security.User;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import com.sm.pfprod.web.util.ImageCodeUtil;
import com.sm.pfprod.web.util.SysUserAuthUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: PfUserRestController
 * @Description: 用户操作
 * @Author yangtongbin
 * @Date 2018/9/14 21:22
 */
@Controller
@RequestMapping(value = "/pf/r/user")
public class PfUserRestController extends BaseController {

    @Resource
    private PfUserService pfUserService;

    @Resource(name = "rsaKeyPairQueue")
    private RsaKeyPairQueue rsaKeyPairQueue;

    @Resource
    private ImageCodeUtil imageCodeUtil;

    /**
     * 新增用户
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveUser(@RequestBody RegisterDto dto, HttpServletRequest request) {
        /* 参数校验 */
        Assert.isTrue(dto.getUsername() != null, "username");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), "password");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoles()), "roles");

        User user = SecurityContext.currentUser();
        dto.setOperator(user.getUsername());
        if (!SysUserAuthUtils.isPlatOrSuper() && !dto.getIdOrg().equals(user.getIdOrg())) {
            throw new BizRuntimeException(ErrorCode.USER_AUTH_EXCEPTION, ErrorMessage.USER_AUTH_EXCEPTION_MSG);
        }

        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        // 密码转化为明文
        String plainPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getPassword());
        dto.setPassword(plainPsw);

        return ResultObject.create("saveUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.saveUser(dto));
    }

    /**
     * 编辑用户
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER_EDIT','ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateUser(@RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getUsername() != null, "username");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoles()), "roles");

        User user = SecurityContext.currentUser();
        dto.setOperator(user.getUsername());
        if (!SysUserAuthUtils.isPlatOrSuper() && !dto.getIdOrg().equals(user.getIdOrg())) {
            throw new BizRuntimeException(ErrorCode.USER_AUTH_EXCEPTION, ErrorMessage.USER_AUTH_EXCEPTION_MSG);
        }
        return ResultObject.create("updateUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.updateUser(dto));
    }

    /**
     * 冻结用户
     *
     * @param dto 用户id集合
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER_FREEZE','ROLE_SUPER')")
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject freezeUser(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto != null || CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        dto.setCurrentUserOrgId(SecurityContext.currentUser().getIdOrg());
        dto.setPlatOrSuper(SysUserAuthUtils.isPlatOrSuper());
        return ResultObject.create("freezeUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.freezeUser(dto));
    }

    /**
     * 删除用户
     *
     * @param dto 用户id集合
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER_DEL','ROLE_SUPER')")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delUser(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto != null || CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        dto.setCurrentUserOrgId(SecurityContext.currentUser().getIdOrg());
        dto.setPlatOrSuper(SysUserAuthUtils.isPlatOrSuper());
        return ResultObject.create("delUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.delUser(dto));
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
                ResultObject.DATA_TYPE_OBJECT, pfUserService.updatePsw(dto));
    }

    /**
     * 密码重置
     *
     * @param request
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER_RESET_PSW','ROLE_SUPER')")
    @RequestMapping(value = "/resetPsw", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject resetPsw(HttpServletRequest request, @RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getPassword() != null, "password");
        dto.setPlatOrSuper(SysUserAuthUtils.isPlatOrSuper());
        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        // 密码转化为明文
        String plainPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getPassword());
        dto.setPassword(plainPsw);
        return ResultObject.create("resetPsw", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.resetPsw(dto));
    }

    @RequestMapping(value = "/register/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject registerUser(HttpServletRequest request, @RequestBody UserRegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getEmail()), "email");
        Assert.isTrue(StringUtils.isNotBlank(dto.getEmailVercode()), "emailVercode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getOrgName()), "orgName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), "password");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPhone()), "phone");
        Assert.isTrue(StringUtils.isNotBlank(dto.getUsername()), "orgName");

        RsaKeyPair keyPair = rsaKeyPairQueue.getRsaKeyQueue(request);
        // 密码转化为明文
        String plainPsw = RSAEncrypt.decryptByPrivateKeyStr(keyPair.getPrivateKey(), dto.getPassword());
        dto.setPassword(plainPsw);
        return ResultObject.createSuccess("registerUser", ResultObject.DATA_TYPE_OBJECT,
                pfUserService.registerUser(dto));
    }

    @RequestMapping(value = "/register/sendEmailVcode", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject sendRegisterEmailVcode(HttpServletRequest request, @RequestBody RegisterVcodeDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getEmail()), "email");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPhotoVercode()), "photoVercode");
        // 验证码校验
        if (!dto.getPhotoVercode()
                .equalsIgnoreCase((String) request.getSession().getAttribute(imageCodeUtil.getSessionKey()))) {
            throw new BizRuntimeException("photoVcodeError", "请输入正确的图片验证码");
        }
        return ResultObject.createSuccess("sendRegisterEmailVcode", ResultObject.DATA_TYPE_OBJECT,
                pfUserService.sendRegisterEmailVcode(dto.getEmail(), null));
    }


}
