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
import com.niit.todo.service.service.ToDoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceImplTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoServiceImpl;

    private ToDo todo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        todo = new ToDo(1L, "Test Todo", "Test Description", false);

    }

    @Test
    void testCreateTodo_TodoFoundException() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        assertThrows(TodoFoundException.class, () -> {
            toDoServiceImpl.createTodo(todo);
        });

        verify(toDoRepository, times(1)).findById(todo.getId());
        verify(toDoRepository, never()).save(any(ToDo.class));
    }

    @Test
    void testCreateTodo_Success() throws TodoFoundException {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.empty());
        when(toDoRepository.save(todo)).thenReturn(todo);

        ToDo createdTodo = toDoServiceImpl.createTodo(todo);

        assertEquals(todo, createdTodo);
        verify(toDoRepository, times(1)).findById(todo.getId());
        verify(toDoRepository, times(1)).save(todo);
    }

    @Test
    void testGetTodoById_TodoNotFoundException() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> {
            toDoServiceImpl.getTodoById(todo.getId());
        });

        verify(toDoRepository, times(1)).findById(todo.getId());
    }

    @Test
    void testGetTodoById_Success() throws TodoNotFoundException {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        ToDo foundTodo = toDoServiceImpl.getTodoById(todo.getId());

        assertEquals(todo, foundTodo);
        verify(toDoRepository, times(1)).findById(todo.getId());
    }

    @Test
    void testUpdateTodo_TodoNotFoundException() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> {
            toDoServiceImpl.updateToDo(todo.getId(), todo);
        });

        verify(toDoRepository, times(1)).findById(todo.getId());
        verify(toDoRepository, never()).save(any(ToDo.class));
    }

    @Test
    void testUpdateTodo_Success() throws TodoNotFoundException {
        ToDo updatedTodo = new ToDo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated ToDo");
        updatedTodo.setDescription("Updated description");
        updatedTodo.setCompleted(true);

        // Mock the behavior to return the same result on both calls
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        when(toDoRepository.save(todo)).thenReturn(updatedTodo);

        ToDo result = toDoServiceImpl.updateToDo(todo.getId(), updatedTodo);

        assertEquals(updatedTodo.getTitle(), result.getTitle());
        assertEquals(updatedTodo.getDescription(), result.getDescription());
        assertTrue(result.isCompleted());

        // Verify findById is called twice, and save is called once
        verify(toDoRepository, times(2)).findById(todo.getId());
        verify(toDoRepository, times(1)).save(any(ToDo.class));
    }


    @Test
    void testGetAllTodos() {
        List<ToDo> todos = new ArrayList<>();
        todos.add(todo);

        when(toDoRepository.findAll()).thenReturn(todos);

        List<ToDo> result = toDoServiceImpl.getAllTodos();

        assertEquals(1, result.size());
        assertEquals(todo, result.get(0));
        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTodoById_TodoNotFoundException() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> {
            toDoServiceImpl.deleteTodoById(todo.getId());
        });

        verify(toDoRepository, times(1)).findById(todo.getId());
        verify(toDoRepository, never()).deleteById(todo.getId());
    }

    @Test
    void testDeleteTodoById_Success() throws TodoNotFoundException {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        boolean result = toDoServiceImpl.deleteTodoById(todo.getId());

        assertTrue(result);
        verify(toDoRepository, times(1)).findById(todo.getId());
        verify(toDoRepository, times(1)).deleteById(todo.getId());
    }
}
