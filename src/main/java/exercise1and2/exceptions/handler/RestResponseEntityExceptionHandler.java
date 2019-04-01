package exercise1and2.exceptions.handler;

import exercise1and2.exceptions.CampaignAlreadyRegisteredException;
import exercise1and2.exceptions.CampaignNotFoundException;
import exercise1and2.exceptions.PartnerAlreadyRegisteredException;
import exercise1and2.exceptions.PartnerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CampaignAlreadyRegisteredException.class })
    protected ResponseEntity<Object> handleCampaignAlreadyRegistered(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Esta campanha já está cadastrada!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.OK, request);
    }

    @ExceptionHandler(value = { CampaignNotFoundException.class })
    protected ResponseEntity<Object> handleCampaignNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Campanha não encontrada!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { PartnerAlreadyRegisteredException.class })
    protected ResponseEntity<Object> handlePartnerAlreadyRegistered(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Este cliente já está cadastrado!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.OK, request);
    }

    @ExceptionHandler(value = { PartnerNotFoundException.class })
    protected ResponseEntity<Object> handlePartnerNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Cliente não encontrado!";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
