package com.sm.pfprod.service.user.login.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.common.PfCommonListParam;
import com.sm.open.core.facade.model.param.pf.user.PfUserParam;
import com.sm.open.core.facade.model.param.pf.user.login.RegisterParam;
import com.sm.open.core.facade.model.param.pf.user.login.UpdatePswParam;
import com.sm.open.core.facade.model.param.pf.user.register.UserRegisterParam;
import com.sm.open.core.facade.model.result.pf.common.auth.UserInfoResult;
import com.sm.open.core.facade.model.result.pf.user.login.PfUsersResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.user.login.LoginClient;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.dto.user.register.UserRegisterDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.user.login.PfUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfUserService")
public class PfUserServiceImpl implements PfUserService {

    @Resource
    private LoginClient loginClient;

    @Override
    public PageResult listUsers(PfUserDto dto) {
        PfPageResult<PfUsersResult> result = loginClient.listUsers(BeanUtil.convert(dto, PfUserParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean saveUser(RegisterDto dto) {
        CommonResult<Boolean> result = loginClient.saveUser(BeanUtil.convert(dto, RegisterParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updateUser(RegisterDto dto) {
        CommonResult<Boolean> result = loginClient.updateUser(BeanUtil.convert(dto, RegisterParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delUser(PfCommonListDto dto) {
        CommonResult<Boolean> result = loginClient.delUser(BeanUtil.convert(dto, PfCommonListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean freezeUser(PfCommonListDto dto) {
        CommonResult<Boolean> result = loginClient.freezeUser(BeanUtil.convert(dto, PfCommonListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }


    @Override
    public boolean updatePsw(UpdatePswDto dto) {
        CommonResult<Boolean> result = loginClient.updatePsw(BeanUtil.convert(dto, UpdatePswParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean resetPsw(RegisterDto dto) {
        CommonResult<Boolean> result = loginClient.resetPsw(BeanUtil.convert(dto, RegisterParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public UserInfo selectUser(String userName) {
        CommonResult<UserInfoResult> result = loginClient.selectUser(userName);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), UserInfo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean registerUser(UserRegisterDto dto) {
        CommonResult<Boolean> result = loginClient.registerUser(BeanUtil.convert(dto, UserRegisterParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean sendRegisterEmailVcode(String email, Long userId) {
        CommonResult<Boolean> result = loginClient.sendRegisterEmailVcode(email, userId);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

}
