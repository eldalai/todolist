package com.gl.todolist.web.test.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.TaskStatus;
import com.gl.todolist.domain.TaskType;

public class TestUtils {

    //Nombres de los campos que Spring Security espera que sea posteen para logear un usuario 
    public static final String SPRING_SECURITY_USER_NAME_FIELD = "j_username";
    public static final String SPRING_SECURITY_PASSWORD_FIELD = "j_password";
        
    
    public static String createJsonUser(String name, String password) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();

        if (name != null) {
            objectNode.put("name", name);
        }
        if (password != null) {
            objectNode.put("password", password);
        }

        return objectNode.toString();
    }
    
    public static String createJsonTask() {
        Task task = createTask();
        return convertToJson(task);
    }

    
    public static <T> T readValue(String content, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer el objeto de  " + content);
        }
        
    }
        
   
    public static String convertToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("No se pudo serializar object " + object, e);
        }
    }

    public static Task createTask() {
        Task task = new Task();
        task.setTaskStatus(TaskStatus.PENDING);
        task.setTitle("Ir al supermercado");
        task.setTaskType(TaskType.URGENT);
        return task;
    }

    
    
    
}
