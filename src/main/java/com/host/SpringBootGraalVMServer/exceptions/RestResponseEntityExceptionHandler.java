package com.host.SpringBootGraalVMServer.exceptions;

import com.host.SpringBootGraalVMServer.model.AppError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class}) /* отвечает за авторизацию */
    public ResponseEntity<AppError> handleAuthenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        AppError error = new AppError(ex.getMessage());
        if (response.getHeader("error") != null)
            error.setMessage(response.getHeader("error"));

        return ResponseEntity.status(response.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<AppError> handleAccessDeniedException(Exception ex, WebRequest request) {
        AppError response = new AppError("AccessDeniedException; " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<AppError> handleUserOrgNotFoundException(Exception ex, WebRequest request) {

        AppError response = new AppError("UserNotFoundException; ");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<AppError> handleBadCredentialsException(Exception ex, WebRequest request) {

        AppError response = new AppError("BadCredentialsException; ");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler({UserNotCreatedException.class})
    public ResponseEntity<AppError> handleUserOrgNotCreatedException(Exception ex, WebRequest request) {

        AppError response = new AppError("UserNotCreatedException; " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler({UserNotUpdatedException.class})
    public ResponseEntity<AppError> handleUserOrgNotUpdatedException(Exception ex, WebRequest request) {

        AppError response = new AppError("UserNotUpdatedException; " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


}
