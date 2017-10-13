package com.sm.pfprod.service.security.impl;

import com.sm.pfprod.service.security.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {


	@Override
	public List<String> findAuthoritiesByUserId(Long userId) {
		List<String> authorities = new ArrayList<String>();

		return authorities;
	}

}
