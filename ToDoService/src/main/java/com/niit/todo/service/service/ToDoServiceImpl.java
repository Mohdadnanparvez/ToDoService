/*
 * Author :Mohd adnan parvez
 * Date : 19-10-2024
 * Created with : intellig IDEA Communit EDITION
 */


package com.niit.todo.service.service;

import com.niit.todo.service.domain.ToDo;
import com.niit.todo.service.exception.TodoFoundException;
import com.niit.todo.service.exception.TodoNotFoundException;
import com.niit.todo.service.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService{
    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }


    @Override
    public ToDo createTodo(ToDo toDo) throws TodoFoundException {
        if (toDoRepository.findById(toDo.getId()).isPresent()) {
            throw new TodoFoundException("Todo is found");
        }
        return toDoRepository.save(toDo);
    }

    @Override
    public ToDo getTodoById(Long id) throws TodoNotFoundException {
        Optional<ToDo> optionalTodo = toDoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new TodoNotFoundException("Todo Not Found");
        }
        return optionalTodo.get();
    }

    @Override
    public ToDo updateToDo(Long id,ToDo toDo) throws TodoNotFoundException {
        if (toDoRepository.findById(id).isEmpty()){
            throw new TodoNotFoundException("Todo Not Found");
        }
        ToDo existingTodo = toDoRepository.findById(id).get();

        existingTodo.setTitle(toDo.getTitle());
        existingTodo.setDescription(toDo.getDescription());
        existingTodo.setCompleted(toDo.isCompleted());

        return toDoRepository.save(existingTodo);
    }

    @Override
    public List<ToDo> getAllTodos() {
        return toDoRepository.findAll();
    }

    @Override
    public boolean deleteTodoById(Long id) throws TodoNotFoundException {
        if (toDoRepository.findById(id).isEmpty()){
            throw new TodoNotFoundException("Todo Not Found");
        }
        toDoRepository.deleteById(id);
        return true;
    }

    @Override
    public ToDo getByTitle(String title){
        return toDoRepository.findByTitle(title);
    }



}
