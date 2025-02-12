package com.sharedLibrary.dto;

public class ApiResponse<T> {
	
	private String statusCode;
	
	private String statusMsg;
	
	private T result;
	
	

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	

	public ApiResponse(String statusCode, String statusMsg, T result) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.result = result;
	}

	@Override
	public String toString() {
		return "ApiResponse [statusCode=" + statusCode + ", statusMsg=" + statusMsg + ", result=" + result + "]";
	}

	
	
	

}
