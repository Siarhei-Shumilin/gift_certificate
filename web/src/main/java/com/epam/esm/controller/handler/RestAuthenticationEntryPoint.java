package com.epam.esm.controller.handler;

import com.epam.esm.exception.ExceptionType;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException { ;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode", ExceptionType.FAILED_AUTHENTICATION.getCustomCode());
        jsonObject.put("message", ExceptionType.FAILED_AUTHENTICATION.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(jsonObject.toString());
    }
}