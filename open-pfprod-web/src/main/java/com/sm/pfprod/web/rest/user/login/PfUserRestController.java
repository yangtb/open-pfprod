package com.sm.pfprod.web.rest.user.login;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.utils.rsa.RSAEncrypt;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.facade.user.PfUserFacade;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
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
 * 登陆
 */
@Controller
@RequestMapping(value = "/pf/r/user")
public class PfUserRestController extends BaseController {

    @Resource
    private PfUserFacade pfUserFacade;

    @Resource(name = "rsaKeyPairQueue")
    private RsaKeyPairQueue rsaKeyPairQueue;

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
        dto.setOperator(CurrentUserUtils.getCurrentUsername());

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
    @PreAuthorize("hasAnyRole('ROLE_USER_EDIT','ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateUser(@RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getUsername() != null, "username");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoles()), "roles");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("updateUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.updateUser(dto));
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
        return ResultObject.create("freezeUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserFacade.freezeUser(dto.getList()));
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
    @PreAuthorize("hasAnyRole('ROLE_USER_RESET_PSW','ROLE_SUPER')")
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
