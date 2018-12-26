package com.test.springboot.common.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Integer code;
	private String msg;
	
	public BusinessException() {
		super();
	}
	public BusinessException(String message) {
		super(message);
		this.msg = message;
	}
	public BusinessException(String message, Integer code) {
		super(message);
		this.msg = message;
		this.code = code;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
