package com.sm.pfprod.service.user.security.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.pfprod.integration.user.login.LoginClient;
import com.sm.pfprod.service.user.security.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private LoginClient loginClient;

    @Override
    public List<String> findAuthoritiesByUserId(Long userId, String roleType) {
        CommonResult<List<String>> result = loginClient.findAuthoritiesByUserId(userId, roleType);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

}
