/*
 * Author :Mohd adnan parvez
 * Date : 19-10-2024
 * Created with : intellig IDEA Communit EDITION
 */


package com.niit.todo.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND,reason = "Todo is found")
public class TodoFoundException extends Exception {
    public TodoFoundException(String message) {
        super(message);
    }
}
