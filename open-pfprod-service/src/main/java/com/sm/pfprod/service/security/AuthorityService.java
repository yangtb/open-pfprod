package com.sm.pfprod.service.security;

import java.util.List;

/**
 * @ClassName: AuthorityService
 * @Description: 用户权限相关的接口
 * @Author yangtongbin
 * @Date 2017/10/12 21:44
 */
public interface AuthorityService {

    /**
     * 根据用户ID查找用户的权限编码集合
     *
     * @param userId 用户id
     * @return
     */
    List<String> findAuthoritiesByUserId(Long userId);

}
