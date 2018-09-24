package com.sm.pfprod.integration.user.login;

import com.sm.open.core.facade.model.param.pf.user.PfUserParam;
import com.sm.open.core.facade.model.param.pf.user.login.RegisterParam;
import com.sm.open.core.facade.model.param.pf.user.login.UpdatePswParam;
import com.sm.open.core.facade.model.param.pf.user.register.UserRegisterParam;
import com.sm.open.core.facade.model.result.pf.common.auth.UserInfoResult;
import com.sm.open.core.facade.model.result.pf.user.login.PfUsersResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.user.login.PfUserFacade;
import com.sm.pfprod.model.dto.user.register.UserRegisterDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LoginClient {

    @Resource
    private PfUserFacade pfUserFacade;


    public PfPageResult<PfUsersResult> listUsers(PfUserParam param) {
        return pfUserFacade.listUsers(param);
    }


    public CommonResult<Boolean> saveUser(RegisterParam param) {
        return pfUserFacade.saveUser(param);
    }


    public CommonResult<Boolean> updateUser(RegisterParam param) {
        return pfUserFacade.updateUser(param);
    }

    public CommonResult<Boolean> delUser(List<Long> users) {
        return pfUserFacade.delUser(users);
    }


    public CommonResult<Boolean> freezeUser(List<Long> users) {
        return pfUserFacade.freezeUser(users);
    }


    public CommonResult<Boolean> updatePsw(UpdatePswParam param) {
        return pfUserFacade.updatePsw(param);
    }


    public CommonResult<Boolean> resetPsw(RegisterParam param) {
        return pfUserFacade.resetPsw(param);
    }

    public CommonResult<UserInfoResult> selectUser(String userName) {
        return pfUserFacade.selectUser(userName);
    }

    public CommonResult<List<String>> findAuthoritiesByUserId(Long userId, String roleType) {
        return pfUserFacade.findAuthoritiesByUserId(userId, roleType);
    }

    public CommonResult<Boolean> registerUser(UserRegisterParam param) {
        return pfUserFacade.registerUser(param);
    }

    public CommonResult<Boolean> sendRegisterEmailVcode(String email, Long userId) {
        return pfUserFacade.sendRegisterEmailVcode(email, userId);
    }
}
