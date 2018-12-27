package com.xylem.hackathon.config;


public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;

    public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public JwtAuthenticationResponse(String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken= refreshToken;
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
