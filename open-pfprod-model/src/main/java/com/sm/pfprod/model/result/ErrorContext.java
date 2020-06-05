package com.sm.pfprod.model.result;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ErrorContext
 * @Description: 错误上下文，错误堆栈
 * @Author yangtongbin
 * @Date 2017/9/13 16:37
 */
public class ErrorContext {
	
	/** 错误堆栈 */
	private List<CommonError> errorStack = new ArrayList<CommonError>();
	
	public ErrorContext(){
	}
	
	public ErrorContext(List<CommonError> errorStack) {
		this.errorStack = errorStack;
	}

	public List<CommonError> getErrorStack() {
		return errorStack;
	}

	public void setErrorStack(List<CommonError> errorStack) {
		this.errorStack = errorStack;
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
