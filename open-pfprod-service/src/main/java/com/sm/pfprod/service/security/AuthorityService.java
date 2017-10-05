package com.sm.pfprod.service.security;

import java.util.List;

/**
 * @ClassName: AuthorityService
 * @Description: 用户权限相关的接口
 */
public interface AuthorityService {

	/**
	 * 根据用户ID查找用户的权限编码集合
	 * @param userId
	 * @return
	 */
	List<String> findAuthoritiesByUserId(Long userId);

}
