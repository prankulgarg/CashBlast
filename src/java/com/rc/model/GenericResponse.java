/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.model;

/**
 *
 * @author Admin
 */
public class GenericResponse {
	private String status;
	private String message;
	private int statusCode;
	private Object userInfo = null;

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }
    



	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "GenericResponse{" + "status=" + status + ", message=" + message + '}';
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String i) {
		this.status = i;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
