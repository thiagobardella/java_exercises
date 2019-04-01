package exercise1and2.exceptions.handler;

import exercise1and2.exceptions.CampaignAlreadyRegisteredException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value = {CampaignAlreadyRegisteredException.class})
//    protected ResponseEntity<Object> handleCampaignAlreadyRegistered(RuntimeException ex, WebRequest request) {
//        String body = "Campaign already registered!";
//        return handleException(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }

}
