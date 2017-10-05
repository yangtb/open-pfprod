package com.sm.pfprod.web.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName: YtGrantedAuthority
 * @Description: 角色权限资源授权体
 * @author 王勇琳
 * @date 2017年6月29日 下午5:26:33
 */
public class PfGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -1194969608168682514L;

	private String authority;
	
	public PfGrantedAuthority() {
	}
	
	public PfGrantedAuthority(String authority) {
		this.authority = authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(this.authority);
        }
        if (obj instanceof GrantedAuthority) {
            GrantedAuthority attr = (GrantedAuthority) obj;
            return authority.equals(attr.getAuthority());
        }
        return false;
	}
	
	@Override
    public int hashCode() {
        return authority.hashCode();
    }

	@Override
    public String toString() {
        return authority;
    }
	
}
