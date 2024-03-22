package com.example.security_study.lib;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Log4j2
@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(RestException.class)
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public RestException restException(RestException e){
        return RestException.build(e.getMessage() , 500);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public RestException userNotFoundException(UsernameNotFoundException usernameNotFoundException){
        return RestException.build(usernameNotFoundException.getMessage(), 406);
    }

    @ExceptionHandler(BindException.class)
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public RestException validationBindException(BindException bindException){
        return RestException.build(bindException.getMessage(), 405);
    }


//    @ExceptionHandler(Exception.class)
//    @Order(value = Ordered.HIGHEST_PRECEDENCE)
//    public RestException commonException(Exception e){
//        return RestException.build("서버 오류" , 500);
//    }
}
