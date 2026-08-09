package com.swiggy.swiggy_backend.dto;



public class LoginResponse {

    private String token;
    private String message;

   

    public String getToken() {
        return token;
    }
    
 public LoginResponse() {
		
		
	}
    

    public LoginResponse(String token, String message) {
		
		this.token = token;
		this.message = message;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
    	return message;
    }
}