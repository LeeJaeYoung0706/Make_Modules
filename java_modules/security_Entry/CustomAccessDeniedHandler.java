package com.keti.iam.idthub.util.security;

import com.keti.iam.idthub.util.exception.JwtExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인가 실패 시 핸들러 구현체
 */
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ValidationFailResponse validationFailResponse;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        validationFailResponse.validationResponse(response , JwtExceptionEnum.findByErrorReason(accessDeniedException.getMessage()));
    }
}
