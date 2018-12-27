package com.xylem.hackathon.refreshTokenendpoint;

import org.springframework.stereotype.Component;




public interface TokenExtractor {
    public String extract(String payload);
}
