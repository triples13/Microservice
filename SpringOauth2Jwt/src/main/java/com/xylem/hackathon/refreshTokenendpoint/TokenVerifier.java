package com.xylem.hackathon.refreshTokenendpoint;

import org.springframework.stereotype.Component;



public interface TokenVerifier {
    public boolean verify(String jti);
}
