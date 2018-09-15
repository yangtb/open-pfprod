package com.sm.pfprod.web.security;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CustomUser
 * @Description: 自定义登录认证用户
 */
public class CustomUser extends PfUserDetails {
	private static final long serialVersionUID = -8598856660761210306L;

	/** 用来配置明文密码备注使用的 */
	private String rawpassword;
	
	public CustomUser(){
		// userId=-1的用户表示该用户在数据库中不存在
		this.setUserId(-1L);
	}
	
	public void setCustomAuthoritiesAsString(String customAuthoritiesAsString) {
		if (StringUtils.isEmpty(customAuthoritiesAsString)) {
			this.setAuthorityMap(new HashMap<String, GrantedAuthority>());
		} else {
			customAuthoritiesAsString = customAuthoritiesAsString.trim();
			Validate.isTrue(customAuthoritiesAsString.matches("^\\s*ROLE_[a-zA-Z0-9-_]+([,| |\t]+ROLE_[a-zA-Z0-9-_]+)*\\s*$"));
			String[] auths = customAuthoritiesAsString.split("[,| |\t]+");
			Map<String, GrantedAuthority> authorityMap = new HashMap<String, GrantedAuthority>();
			for (String auth : auths) {
				auth = auth.trim();
				authorityMap.put(auth, new PfGrantedAuthority(auth));
			}
			this.setAuthorityMap(authorityMap);
		}
	}
	
	public String getRawpassword() {
		return rawpassword;
	}

	public void setRawpassword(String rawpassword) {
		this.rawpassword = rawpassword;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
