package com.keti.iam.idthub.util.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keti.iam.idthub.util.exception.JwtExceptionEnum;
import com.keti.iam.idthub.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  security exception handler 리스폰 객체
 */
public class ValidationFailResponse {

    public void validationResponse(HttpServletResponse response , JwtExceptionEnum jwtEnum) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Response.builder(jwtEnum.getERROR() , jwtEnum.getCode()).build()
                )
        );
    }
}
