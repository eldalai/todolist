package com.gl.todolist.web.test.rest;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static com.gl.todolist.web.test.rest.TestUtils.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:application-context-web.xml")
public abstract class RestBaseTest {

    // Datos para armar objetos de tests
    public static String name1 = "pepito1@gmail.com";

    public static String name2 = "pepito2@gmail.com";

    public static String pass1 = "zaraza1";

    public static String pass2 = "zaraza2";

    private static final Logger logger = LoggerFactory.getLogger(RestBaseTest.class);

    @BeforeClass
    public static void setUpClass() throws Exception {
        initJNDIContextForTests();
    }

    private static void initJNDIContextForTests() throws NamingException {
        SimpleNamingContextBuilder ctxBuilder;
        ctxBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        ctxBuilder.bind("java:comp/env/todolist/url",
                "http://localhost:8080/todolist-web/backbone/index.html");
        ctxBuilder.bind("java:comp/env/db/hbmDialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        ctxBuilder.bind("java:comp/env/db/type", "MYSQL");
        ctxBuilder.bind("java:comp/env/db/jdbcDriver", "com.mysql.jdbc.Driver");
        ctxBuilder.bind("java:comp/env/db/url", "mysql://todolist:todo@localhost:3306/todolist");
        ctxBuilder.bind("java:comp/env/db/user", "todolist");
        ctxBuilder.bind("java:comp/env/db/password", "todo");
        ctxBuilder.bind("java:comp/env/db/showSql", "true");

        ctxBuilder.bind("java:comp/env/mail/serverUrl", "localhost");
        ctxBuilder.bind("java:comp/env/mail/serverPort", "62525");
        ctxBuilder.bind("java:comp/env/mail/serverUser", "user");
        ctxBuilder.bind("java:comp/env/mail/serverPassword", "pass");
    }

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Autowired
    //El filtro que contiene toda la cadena de filtro de Spring Security.
    //La configuracion se lee de "application-context-web.xml"
    private FilterChainProxy springSecurityFilterChain;

    protected MediaType jsonType = MediaType.parseMediaType("application/json");

    protected MediaType jsonUTF8Type = MediaType.parseMediaType("application/json;charset=UTF-8");

    @Autowired
    protected DataBaseHelper dataBaseHelper;



    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .defaultRequest(get("/")
                .accept(jsonType)
                .contentType(jsonUTF8Type))
                .addFilters(this.springSecurityFilterChain)
                .alwaysDo(print()).build();
    }

    protected static Set<Class> testClassesAlreadyStarted = new HashSet<Class>();

    /**
     * Si bien usando @Before este método se ejecuta antes de cada test, la
     * limpiza de la BD se ejecuta una sola vez al principio y cada metodo de
     * test ve las modificaciones realizadas por los otros metodos de cada test.
     */
    @Before
    public void clearBD() {
        if (!testClassesAlreadyStarted.contains(this.getClass())) {
            this.dataBaseHelper.clearDB();
            testClassesAlreadyStarted.add(this.getClass());
        }

    }

    protected void createUser1() throws Exception {
        String user1 = createJsonUser(name1, pass1);
        this.mockMvc.perform(post("/users")
                            .content(user1))
                            .andExpect(status().isCreated())
                            .andExpect(jsonPath("$.name").value(name1))
                            .andExpect(jsonPath("$.id").value(notNullValue()));
    }


}