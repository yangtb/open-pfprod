package com.sm.pfprod.model.result;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @ClassName: Result
 * @Description: 返回结果
 * @Author yangtongbin
 * @Date 2017/9/13 16:21
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1757313404681564926L;
	
	/** 是否成功 */
	private Boolean isSuccess;
	
	/** 业务错误编码，isSuccess为false时必须 */
	private String errorCode;
	
	/** 业务错误描述，isSuccess为false时必须 */
	private String errorDesc;
	
	/** 用户显示客户端的消息，可选 */
	private String displayMsg;
	
	/** 错误异常堆栈信息，调用多个TR服务的异常堆积 */
	private ErrorContext errorContext;
	
	/** 错误参数集合，比如某多个校验失败的结果集 */
	private Object[] errorParameters;
	
	public Result(){
		
	}
	
	public Result(Boolean isSuccess, String errorCode, String errorDesc,
                  String displayMsg, ErrorContext errorContext,
                  Object[] errorParameters) {
		this.isSuccess = isSuccess;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.displayMsg = displayMsg;
		this.errorContext = errorContext;
		this.errorParameters = errorParameters;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
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

	public String getDisplayMsg() {
		return displayMsg;
	}

	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}

	public ErrorContext getErrorContext() {
		return errorContext;
	}

	public void setErrorContext(ErrorContext errorContext) {
		this.errorContext = errorContext;
	}

	public Object[] getErrorParameters() {
		return errorParameters;
	}

	public void setErrorParameters(Object[] errorParameters) {
		this.errorParameters = errorParameters;
	}

	@Override
	public String toString() {
		return "Result [isSuccess=" + isSuccess + "; errorCode=" + errorCode + "; errorDesc=" + errorDesc
				+ "; displayMsg=" + displayMsg + "; errorContext=" + errorContext + "; errorParameters="
				+ Arrays.toString(errorParameters) + "]";
	}

	public static boolean checkSuccess(Result result) {
		return result != null && result.isSuccess;
	}

}
