package com.xylem.hackathon.filter;

import java.util.Set;


import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


/*
 * run method will pass the tokens to backend
 */

@Component
public class TokenRelayFilter extends ZuulFilter {

	@Override
	public Object run()   {
		System.out.println("in token relay of api gateway");
		RequestContext  ctx=RequestContext.getCurrentContext();
		Set<String> headers = (Set<String>) ctx.get("ignoredHeaders");
		
        // JWT tokens should be relayed to the resource servers
		headers.remove("authorization");
        
        
		return null;
	}

	@Override
	public boolean shouldFilter() {
	
		return true;
	}

	@Override
	public int filterOrder() {
	
		return 1000;
	}

	@Override
	public String filterType() {
	
		return "pre";
	}

}
