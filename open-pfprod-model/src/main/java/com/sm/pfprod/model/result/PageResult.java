package com.sm.pfprod.model.result;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PageResult
 * @Description: 分页结果
 * @Author yangtongbin
 * @Date 2017/9/8 11:22
 */
public class PageResult<T> implements Serializable {
	private static final long serialVersionUID = -5088185464980438586L;

	private String code;

	private String msg = "";

	private Long count = 0L;

	private List<T> data = new ArrayList<T>();

	private String ext;

	public PageResult() {
		this.code = code;
	}

	public PageResult(String code, String msg, Long count, List<T> data) {
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	public static <T> PageResult<T> create(String code, String msg, Long count, List<T> data) {
		return new PageResult<T>(code, msg, count, data);
	}

	public static <T> PageResult<T> create(String msg, Long count, List<T> data) {
		return new PageResult<T>("0", msg, count, data);
	}

	public static <T> PageResult<T> create(Long count, List<T> data) {
		return new PageResult<T>("0", "",count, data);
	}

	public static <T> PageResult<T> create(List<T> data) {
		return new PageResult<T>("0", "", (data == null) ? 0L : data.size(), data);
	}

	public static <T> PageResult<T> create(String code, String msg) {
		return new PageResult<T>("0", msg, 0L, null);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
