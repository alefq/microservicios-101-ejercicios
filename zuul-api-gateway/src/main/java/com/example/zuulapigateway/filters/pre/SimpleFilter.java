package com.example.zuulapigateway.filters.pre;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

public class SimpleFilter extends ZuulFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.addZuulRequestHeader("X-JOKO-MICROSERVICES-101", "Unagi!");
        LOGGER.info("Ejemplo de PRE filter");
        LOGGER.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }

}
