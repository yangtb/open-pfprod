package com.sm.pfprod.web.security;


import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: User
 * @Description: 平台系统用户
 * @Author yangtongbin
 * @Date 2017/9/2 22:54
 */
public class User implements Serializable {

	private static final long serialVersionUID = 6427496640721395199L;

	/** 对应数据库的主键id */
	private Long userId;
	
	/** 登录类型，"user_name","phone_no","email" */
	private String loginType;
	
	/** 登录用户名，可能是"user_name"、"phone_no"、"email"中的一种 */
	private String username;

	/** 登录密码 */
	private String password;
	
	/** 密码盐值 */
	private String salt;
	
	/** 是否启用,1启用 0停用； */
	private boolean enabled;

	/** 当前用户所在机构id */
	private Long idOrg;

	/** 当前用户所在机构级别 */
	private String fgPlat;

	/** 当前用户所在机构状态 */
	private String fgActive;
	
	/** 用户头像 */
	private String headPhoto;
	
	/** 昵称 */
	private String nickName;
	
	/** 当前客户端ip */
	private String ipAddress;
	
	/** 当前客户端代理信息 */
	private String userAgent;
	
	/** 当前的终端类型：ANDROID:1；IOS:2；PC_BROWSER:3；服务器(SERVER):4；QQ:7；支付宝:8；微信:9；其他:6。 */
	private Integer terminalType;

	/**
	 * 用户拥有角色编辑集合
	 */
	private List<String> roleCodes;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getIdOrg() {
		return idOrg;
	}

	public void setIdOrg(Long idOrg) {
		this.idOrg = idOrg;
	}

	public String getFgPlat() {
		return fgPlat;
	}

	public void setFgPlat(String fgPlat) {
		this.fgPlat = fgPlat;
	}

	public String getFgActive() {
		return fgActive;
	}

	public void setFgActive(String fgActive) {
		this.fgActive = fgActive;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}
}
