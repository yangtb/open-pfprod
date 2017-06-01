package com.sm.pfprod.model.vo.common.auth;

import java.io.Serializable;

/**
 *
 */
public class UserAuthVo implements Serializable {

    private static final long serialVersionUID = -7207464050152759382L;

    private String            accessToken;                            //交换令牌

    private String            userId;                                 //用户的userId

    private Integer           expiresIn;                              //令牌有效期

    private String            reExpiresIn;                            //刷新令牌有效期

    private String            refreshToken;                           //刷新令牌

    public UserAuthVo() {
    }

    public UserAuthVo(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserAuthVo(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getReExpiresIn() {
        return reExpiresIn;
    }

    public void setReExpiresIn(String reExpiresIn) {
        this.reExpiresIn = reExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
