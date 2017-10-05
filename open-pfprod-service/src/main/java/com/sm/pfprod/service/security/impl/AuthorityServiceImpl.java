package com.sm.pfprod.service.security.impl;

import com.sm.pfprod.service.security.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AuthorityServiceImpl
 * @Description: 用户权限相关的接口实现
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {


	@Override
	public List<String> findAuthoritiesByUserId(Long userId) {
		List<String> authorities = new ArrayList<String>();

		return authorities;
	}

}
