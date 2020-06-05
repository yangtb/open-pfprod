package com.sm.pfprod.model.result;

/**
 * @ClassName: CommonResult
 * @Description: 通用返回结果
 * @Author yangtongbin
 * @Date 2017/9/14 10:28
 */
public class CommonResult<T> extends Result {
	
	private T content;

	/**
	 * 将父类转换成子类
	 * @param result
	 * @param <T>
	 * @return
	 */
	public static <T> CommonResult<T> toCommonResult(Result result){
		CommonResult<T> cr = new CommonResult<T>();
		cr.setIsSuccess(result.getIsSuccess());
		cr.setErrorCode(result.getErrorCode());
		cr.setErrorDesc(result.getErrorDesc());
		cr.setDisplayMsg(result.getDisplayMsg());
		cr.setErrorContext(result.getErrorContext());
		cr.setErrorParameters(result.getErrorParameters());
		return cr;
	}
	
	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommonResult [content=" + content + "]";
	}

}
