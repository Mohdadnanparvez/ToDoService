/*
 * Author :Mohd adnan parvez
 * Date : 19-10-2024
 * Created with : intellig IDEA Communit EDITION
 */


package com.niit.todo.service.controller;

import com.niit.todo.service.domain.ToDo;
import com.niit.todo.service.exception.TodoFoundException;
import com.niit.todo.service.exception.TodoNotFoundException;
import com.niit.todo.service.service.ToDoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {
    private final ToDoServiceImpl toDoService;

    @Autowired
    public ToDoController(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    // Create a new Todo
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody ToDo toDo) {
        try {
            ToDo createdTodo = toDoService.createTodo(toDo);
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
        } catch (TodoFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Get a Todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Long id) {
        try {
            ToDo todo = toDoService.getTodoById(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (TodoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Update a Todo by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody ToDo toDo) {
        try {
            ToDo updatedTodo = toDoService.updateToDo(id, toDo);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } catch (TodoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Todo by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long id) {
        try {
            boolean isDeleted = toDoService.deleteTodoById(id);
            if (isDeleted) {
                return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unable to delete the Todo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (TodoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all Todos
    @GetMapping
    public ResponseEntity<List<ToDo>> getAllTodos() {
        List<ToDo> todos = toDoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

}
