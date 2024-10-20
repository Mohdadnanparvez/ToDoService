package com.niit.todo.service.service;

import com.niit.todo.service.domain.ToDo;
import com.niit.todo.service.exception.TodoFoundException;
import com.niit.todo.service.exception.TodoNotFoundException;

import java.util.List;

public interface ToDoService {
    //Create a new todo
   ToDo createTodo(ToDo toDo) throws TodoFoundException;
   //Get Todo by ID
    ToDo getTodoById(Long id) throws TodoNotFoundException;
   //Update a existing todo
    ToDo updateToDo(Long id , ToDo toDo) throws TodoNotFoundException;
   //Get All Todos
   List<ToDo> getAllTodos();
    //Delete Todo by ID
    boolean deleteTodoById(Long id) throws TodoNotFoundException;
}
