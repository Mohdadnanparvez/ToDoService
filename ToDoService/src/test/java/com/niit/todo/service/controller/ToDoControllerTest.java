/*
 * Author :Mohd adnan parvez
 * Date : 19-10-2024
 * Created with : intellig IDEA Community EDITION
 */

package com.niit.todo.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.todo.service.domain.ToDo;
import com.niit.todo.service.exception.TodoFoundException;
import com.niit.todo.service.exception.TodoNotFoundException;
import com.niit.todo.service.service.ToDoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {

    @Mock
    private ToDoServiceImpl toDoService;

    @InjectMocks
    private ToDoController toDoController;

    private MockMvc mockMvc;
    private ToDo todo1;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
        todo1 = new ToDo(1L, "Test Todo", "Test Description", false);
    }

    private static String jsonToString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void createTodoForSuccess() throws Exception {
        when(toDoService.createTodo(any(ToDo.class))).thenReturn(todo1);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(todo1)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).createTodo(any(ToDo.class));
    }

    @Test
    void createTodoForConflict() throws Exception {
        when(toDoService.createTodo(any(ToDo.class))).thenThrow(TodoFoundException.class);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(todo1)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).createTodo(any(ToDo.class));
    }

    @Test
    void getTodoByIdForSuccess() throws Exception {
        when(toDoService.getTodoById(1L)).thenReturn(todo1);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).getTodoById(1L);
    }

    @Test
    void getTodoByIdForNotFound() throws Exception {
        when(toDoService.getTodoById(1L)).thenThrow(TodoNotFoundException.class);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).getTodoById(1L);
    }

    @Test
    void updateTodoForSuccess() throws Exception {
        when(toDoService.updateToDo(1L, todo1)).thenReturn(todo1);

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(todo1)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).updateToDo(1L, todo1);
    }

    @Test
    void updateTodoForNotFound() throws Exception {
        when(toDoService.updateToDo(1L, todo1)).thenThrow(TodoNotFoundException.class);

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(todo1)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).updateToDo(1L, todo1);
    }

    @Test
    void deleteTodoByIdForSuccess() throws Exception {
        when(toDoService.deleteTodoById(1L)).thenReturn(true);

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).deleteTodoById(1L);
    }

    @Test
    void deleteTodoByIdForNotFound() throws Exception {
        when(toDoService.deleteTodoById(1L)).thenThrow(TodoNotFoundException.class);

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).deleteTodoById(1L);
    }

    @Test
    void getAllTodosForSuccess() throws Exception {
        when(toDoService.getAllTodos()).thenReturn(List.of(todo1));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(toDoService, times(1)).getAllTodos();
    }
}
