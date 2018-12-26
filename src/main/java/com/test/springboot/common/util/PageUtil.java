package com.test.springboot.common.util;

import java.util.List;

public class PageUtil <T> {

	private long total = 0;
	private long current = 0;
	private long perpage = 10;
	private List<T> data;
	private String msg = "";
	private int code = 200;
	private boolean success = false;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getCurrent() {
		return current;
	}
	public void setCurrent(long current) {
		this.current = current;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getPerpage() {
		return perpage;
	}
	public void setPerpage(long perpage) {
		this.perpage = perpage;
	}
	
}
