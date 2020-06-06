package com.sm.pfprod.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PfUserDetails
 * @Description: 当前登录用户详情
 * @Author yangtongbin
 * @Date 2017/9/2 22:53
 */
public class PfUserDetails extends User implements UserDetails {
	private static final long serialVersionUID = -2920433765248963774L;
	
	private Map<String, GrantedAuthority> authorityMap;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;

	public PfUserDetails() {}
	
	public Map<String, GrantedAuthority> getAuthorityMap() {
		return authorityMap;
	}

	public void setAuthorityMap(Map<String, GrantedAuthority> authorityMap) {
		this.authorityMap = authorityMap;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorityMap.values();
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		if(CollectionUtils.isEmpty(authorities)){
			return;
		}
		this.authorityMap = new HashMap<String, GrantedAuthority>();
		for (GrantedAuthority auth : authorities) {
			if(!StringUtils.isEmpty(auth.getAuthority())){
				this.authorityMap.put(auth.getAuthority().trim(), auth);
			}
		}
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof PfUserDetails) {
            return getUsername().equals(((PfUserDetails) rhs).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(getUsername()).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(isEnabled()).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        if (!this.authorityMap.isEmpty()) {
            sb.append("当前用户被授予的权限有：");
            boolean first = true;
            for (GrantedAuthority auth : this.authorityMap.values()) {
                if (!first) {
                    sb.append(",");
                }
                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("当前用户没有被授予任何权限！");
        }
        return sb.toString();
    }

}
