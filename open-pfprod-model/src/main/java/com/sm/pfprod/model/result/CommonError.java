package com.sm.pfprod.model.result;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: CommonError
 * @Description: 普通错误
 * @Author yangtongbin
 * @Date 2017/9/13 16:37
 */
public class CommonError {
	
	/** 错误编码 */
	private String errorCode;
	
	/** 错误描述 */
	private String errorDesc;
	
	/** 错误来源，表示该错误来源于哪一套TR服务 */
	private String location;
	
	public CommonError(){
	}
	
	public CommonError(String errorCode, String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	
	public CommonError(String errorCode, String errorDesc, String location) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.location = location;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
