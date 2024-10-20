/*
 * Author :Mohd adnan parvez
 * Date : 19-10-2024
 * Created with : intellig IDEA Communit EDITION
 */


package com.niit.todo.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Todo is not found")
public class TodoNotFoundException extends Exception{
       public TodoNotFoundException(String message) {
        super(message);
    }
}
