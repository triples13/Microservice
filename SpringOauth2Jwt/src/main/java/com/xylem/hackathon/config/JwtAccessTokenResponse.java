package com.xylem.hackathon.config;

import org.springframework.stereotype.Component;

/*
 * class for returning response with Bearer Header
 */
public class JwtAccessTokenResponse {
	 private String accessToken;
	    private String tokenType = "Bearer";
	  

	

		public JwtAccessTokenResponse(String accessToken) {
	        this.accessToken = accessToken;
	       
	    }

	    public String getAccessToken() {
	        return accessToken;
	    }

	    public void setAccessToken(String accessToken) {
	        this.accessToken = accessToken;
	    }

	    public String getTokenType() {
	        return tokenType;
	    }

	    public void setTokenType(String tokenType) {
	        this.tokenType = tokenType;
	    }
}
