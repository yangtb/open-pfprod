package com.sm.pfprod.web.system.user.login;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.facade.role.PfRoleFacade;
import com.sm.pfprod.facade.user.PfUserFacade;
import com.sm.pfprod.model.dto.common.UserDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.common.PfCommonListDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.common.util.CurrentUserUtil;
import com.sm.pfprod.service.user.login.PfUserService;
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
import java.util.List;

/**
 * 登陆
 */
@Controller
@RequestMapping(value = "/user")
public class PfUserRestController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfUserRestController.class);

    @Resource
    private PfUserService pfUserService;
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
    public String modifyPass() {
        return "pages/user/modifyPass";
    }

    @RequestMapping("/form")
    public String form(String formType, Long userId, Model model, HttpServletRequest request) {
        model.addAttribute("formType", formType);
        RsaKeyPair keyPair;
        try {
            keyPair = rsaKeyPairQueue.takeQueue(request);
            model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        } catch (InterruptedException e) {
            logger.error("新智用户时，rsa公私钥队列相关操作异常", e);
        }
        if (StringUtils.equals(formType, "edit")) {
            model.addAttribute("roles", pfRoleFacade.listUserRole(userId));
        } else {
            model.addAttribute("roles", pfRoleFacade.list());
        }
        return "pages/user/userForm";
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
    public ResultObject saveUser(@RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getUsername() != null, "username");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), "password");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoles()), "roles");

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
    @RequestMapping(value = "/update_psw", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updatePsw(HttpServletRequest request, @RequestBody UpdatePswDto dto) {
        UserDto user = CurrentUserUtil.currentUser(request);
        if (user == null || StringUtils.isEmpty(user.getUserName())) {
            throw new BizRuntimeException(ErrorCode.ERROR_NET_150001, "session_user为空，访问被拒绝");
        }
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        LoggerUtil.info(LOGGER, "session中的用户信息：", JSON.toJSONString(user));
        return ResultObject.create("updatePsw", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.updatePsw(dto));
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject logout(HttpServletRequest request) {
        pfUserService.logout();
        return ResultObject.create("logout", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.logout());
    }
}
