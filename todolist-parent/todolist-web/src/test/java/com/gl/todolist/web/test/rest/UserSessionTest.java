package com.gl.todolist.web.test.rest;

import static com.gl.todolist.web.test.rest.TestUtils.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.gl.todolist.domain.Task;

public class UserSessionTest extends RestBaseTest {

    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    private static Task firstTask;
  
    private static MockHttpSession session;
    
    @Test
    public void createUser1Successfully() throws Exception {
        createUser1();
    }

    @Test
    public void loginUser1() throws Exception {
        mockMvc.perform(post("/login")
                        .param(SPRING_SECURITY_USER_NAME_FIELD, name1)
                        .param(SPRING_SECURITY_PASSWORD_FIELD, pass1))
                        .andExpect(sessionExists(name1));
    }

    private ResultMatcher sessionExists(final String userName) throws Exception {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) throws Exception {
                UserSessionTest.session = (MockHttpSession) mvcResult.getRequest().getSession();
                SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                assertEquals(securityContext.getAuthentication().getName(), userName);
            }
        };
    }

    @Test
    public void tryLoginInvalidUserAndFail() throws Exception {
        mockMvc.perform(post("/login")
                        .param(SPRING_SECURITY_USER_NAME_FIELD, name1)
                        .param(SPRING_SECURITY_PASSWORD_FIELD, "mongo"))
                        .andExpect(status().isUnauthorized());
    }

    @Test
    public void getTasksAndShouldByEmpty() throws Exception {
        mockMvc.perform(get("/tasks")
               .accept(jsonType)
               .contentType(jsonUTF8Type)
               .session(UserSessionTest.session))
               .andExpect(status().isOk())
               .andExpect(content().string(equalTo("[]")));
    }
    
    

    
    @Test
    public void addTask() throws Exception {
        MvcResult result = mockMvc.perform(post("/tasks")
                                            .accept(jsonType)
                                            .contentType(jsonUTF8Type)
                                            .content(createJsonTask())
                                            .session(UserSessionTest.session))
                                            .andExpect(status().isCreated())
                                            .andExpect(jsonPath("$.id").value(notNullValue()))
                                            .andReturn();
        
        String json = result.getResponse().getContentAsString();
        firstTask = readValue(json, Task.class);
        
        assertNotNull(firstTask.getId());
        assertTrue(EqualsBuilder.reflectionEquals(createTask(), firstTask, "id"));            
    }


    
    
    
    @Test
    public void getTasksAndShouldHaveOne() throws Exception {
        MvcResult result =  mockMvc.perform(get("/tasks")
                                            .accept(jsonType)
                                            .contentType(jsonUTF8Type)
                                            .session(UserSessionTest.session))
                                            .andExpect(status().isOk())
                                            .andReturn();
        
        String json = result.getResponse().getContentAsString();
        Task[] tasks = readValue(json, Task[].class);
        
        assertEquals(1, tasks.length);
        assertTrue(EqualsBuilder.reflectionEquals(firstTask, tasks[0]));      
    }
    
    
          
    
}
