package com.gl.todolist.web.controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gl.todolist.services.exceptions.UserException;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }    
    
    
    //Originalmente estaba en HttpStatus.NOT_ACCEPTABLE 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ErrorMessage userExistException( UserException ex, WebRequest request ) {
        return createErrorMessage(ex);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        
        return new ResponseEntity<Object>(createErrorMessage(ex), headers, status);        

    }

    private ErrorMessage createErrorMessage(UserException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    private ErrorMessage createErrorMessage(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<String>(fieldErrors.size() + globalErrors.size());
        String error;
        
        for (FieldError fieldError : fieldErrors) {
            error = processFieldError(fieldError);
            //error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        return new ErrorMessage(errors);
    }    
        

    private String processFieldError(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }    
    
    // ***********************************************************************************************
    //                                REFACTOR PENDIENTE 
    // ***********************************************************************************************
    @ResponseStatus(value=HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = SecurityException.class)
    public void securityException( Exception exception, HttpServletResponse response ) throws Throwable{
        returnResponse(exception, response);
    }
    
    
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public void serverException( Exception exception, HttpServletResponse response ) throws Throwable{
        returnResponse(exception, response);
    }
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public void entityNotFoundExcepcion( EntityNotFoundException exception, HttpServletResponse response ) throws Throwable{
        returnResponse(exception, response);
    }

//  @ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
//  @ExceptionHandler(value = UserException.class)
//  public void userExistException( UserException exception, HttpServletResponse response ) throws Throwable{
//      returnResponse(exception, response);
//  }
//  
    
    protected void returnResponse( Exception exception, HttpServletResponse response ) throws Throwable{
        response.setHeader( exception.getMessage(), exception.getClass().getName());
        PrintWriter writer;
        writer = response.getWriter();
        writer.print( exception.getMessage() );
    }    
    

}
