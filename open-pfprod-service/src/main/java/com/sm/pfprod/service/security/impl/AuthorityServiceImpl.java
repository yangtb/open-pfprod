package com.sm.pfprod.service.security.impl;

import com.sm.pfprod.dal.system.authority.AuthorityDao;
import com.sm.pfprod.service.security.AuthorityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	@Resource
	private AuthorityDao authorityDao;

	@Override
	public List<String> findAuthoritiesByUserId(Long userId) {
		List<String> functionCodes = authorityDao.findFunctionCodesByUserId(userId);
		List<String> authorities = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(functionCodes)){
			authorities.addAll(functionCodes);
		}
		return authorities;
	}

}
