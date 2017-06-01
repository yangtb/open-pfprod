package com.sm.pfprod.web.rest.user.login;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.dto.user.login.LoginDto;
import com.sm.pfprod.model.dto.user.login.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.service.common.util.CurrentUserUtil;
import com.sm.pfprod.service.user.login.PfUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 登陆
 */
@Controller
@RequestMapping(value = "/pf/r/user")
public class PfUserRestController {

    private static final Logger     LOGGER = LoggerFactory.getLogger(PfUserRestController.class);

    @Resource
    private PfUserService           pfUserService;

    /**
     * 获取用户列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listUsers(@RequestBody PfUserDto dto) {
        return ResultObject.create("listUsers", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfUserService.listUsers(dto));
    }

    /**
     * 用户注册
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveUser(@RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getUserName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "user_name"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "password"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getIsBlock()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "is_block"));
        Assert.isTrue(CollectionUtils.isEmpty(dto.getRoles()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "roles"));

        return ResultObject.create("saveUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.saveUser(dto));
    }

    /**
     * 编辑注册
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateUser(@RequestBody RegisterDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getUserName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "user_name"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getIsBlock()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "is_block"));
        Assert.isTrue(CollectionUtils.isEmpty(dto.getRoles()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "roles"));

        return ResultObject.create("updateUser", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.updateUser(dto));
    }

    /**
     * 登陆验证
     * @param dto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject login(@RequestBody LoginDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getUserName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "user_name"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "password"));

        return ResultObject.create("login", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.login(dto));
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/update_psw", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updatePsw(HttpServletRequest request, @RequestBody UpdatePswDto dto) {
        User user = CurrentUserUtil.currentUser(request);
        if (user == null || StringUtils.isEmpty(user.getUserName())) {
            throw new BizRuntimeException(ErrorCode.ERROR_NET_150001, "session_user为空，访问被拒绝");
        }
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        LoggerUtil.info(LOGGER, "session中的用户信息：" , JSON.toJSONString(user));
        return ResultObject.create("updatePsw", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfUserService.updatePsw(dto));
    }

    /**
     * 登出
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
