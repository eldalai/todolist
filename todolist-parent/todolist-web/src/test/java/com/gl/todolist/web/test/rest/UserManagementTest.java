package com.gl.todolist.web.test.rest;

import static com.jayway.jsonassert.JsonAssert.collectionWithSize;

import static com.jayway.jsonassert.JsonAssert.emptyCollection;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.gl.todolist.web.test.rest.TestUtils.*; 


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.todolist.domain.User;
import com.gl.todolist.services.exceptions.MessagesExceptions;
import com.jayway.jsonpath.JsonPath;

public class UserManagementTest extends RestBaseTest {

    // Los ID de los usuarios que se crean. Se guardan en variables estáticas
    // para que puedan
    // ser accedidos luego de que se crearon por los test subsiguientes.
    protected static Integer firstId;

    protected static Integer secondId;

    /**
     * Se crean 2 Users que quedan comiteados en la BD, para ser consultados por
     * los siguientes tests. Se hacen las assertions sobre el Json devuelto para
     * verificar que el id vuelva seteado En los métodos createUserX() se
     * utilizan distintas formas de hacer assertions sobre JSON
     */
    @Test
    public void createTwoUsersSuccessfully() throws Exception {
        createUser1();
        createUser2();
    }

    /**
     * Usando el método '.andReturn()' obtengo el 'mvcResult' al cual despues le
     * pido el content para tener el 'json' de respuesta. Con el método estático
     * 'with' (de JsonAssert) obtengo un JsonAsserter con el que se después hago
     * varias assertions sobre el 'json'
     */
    private void createUser2() throws Exception {
        String user2 = createJsonUser(name2, pass2);
        
        MvcResult mvcResult = this.mockMvc.perform(post("/users")
                                                  .content(user2))
                                                  .andExpect(status().isCreated())
                                                  .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        with(json).assertEquals("$.name", name2)
                  .assertThat("$.id", notNullValue())
                  .assertThat("$.task", emptyCollection())
                  .assertEquals("$.stateUser", true)
                  .assertEquals("$.token", null);
    }

    /**
     * Busco los 2 Users existentes, obtengo el id de uno de esos usuarios y
     * después busco por ID ese usuario
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findAllUsers() throws Exception {
        String json = findAllJson();

        with(json).assertThat("$..name", collectionWithSize(equalTo(2)))
                  .assertThat("$..name", hasItems(name1, name2))
                  .assertEquals("$[0].name", name1)
                  .assertEquals("$[1].name", name2);

        firstId = JsonPath.read(json, "$[0].id");
        secondId = JsonPath.read(json, "$[1].id");
    }

    @Test
    public void findById() throws Exception {
        this.mockMvc.perform(get("/users/{id}", firstId))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.name", equalTo(name1)));
    }

    /**
     * TODO revisar como se arman y tiran las EntityNotFoundException porque
     * nunca se están construyendo con un mensaje y entonces cuando se setea el
     * header con ex.getMessage() pincha con una RuntimeException porque el
     * header no pueder ser null. El mensaje, en este caso, lo definí yo para
     * que este test no pinchara
     */
    @Test
    public void tryFindByInvalidIdAndFail() throws Exception {
        this.mockMvc.perform(get("/users/{id}", -1))
                            .andExpect(status().isNotFound())
                            .andExpect(content().string("User with id -1 does not exist."));
    }

    /**
     * TODO el test "documenta" el funcionamiento actual de los servicios para
     * entenderlos Pero las dudas y problemas planteados sobre este
     * funcionamiento están planteadas en el documento "Temas para ver Arq Ref"
     * https://docs.google.com/a/globallogic.com/document/d/1
     * Jtgjsd6qqS5UqfK6t6fW5aTqE7bn30KrjQC_cmLOgVA/edit
     */
    @Test
    public void tryToCreateUserWithAlreadyExistingNameAndFail() throws Exception {
        String user1 = createJsonUser(name1, pass1);
        
        this.mockMvc.perform(post("/users")
                            .content(user1))
                            .andExpect(status().isNotAcceptable())
                            .andExpect(content().string(MessagesExceptions.USER_EXISTS));
    }

    /**
     * TODO esta aceptando un usuario con password en null. No tiene la
     * anotacion @Valid en el controller y no se valida en el servicio. Tampoco
     * se valida en la capa de persistencia porque estamos usando Hibernate
     * 3.4/JPA 1. Recien en JPA 2.0 las validaciones se disparan autoaticamente.
     * 
     * Es un BUG en el backend
     */
    @Test
    public void tryToCreateUserWithInvalidPasswordAndFailBUG() throws Exception {
        String user1 = createJsonUser("pepito3@gmail.com", null);
        
        this.mockMvc.perform(post("/users")
                            .content(user1))
                            .andExpect(status().isCreated());
    }

    /**
     * TODO actualmente hace la validacion de mail a mano, cuando se podría usar
     * la anotacion que viene en HibernateValidator @Email
     */
    @Test
    public void tryToCreateUserWithInvalidNameAndFail() throws Exception {
        String user1 = createJsonUser("invalidEmail", pass1);
        
        this.mockMvc.perform(post("/users")
                             .content(user1))
                             .andExpect(status().isNotAcceptable())
                             .andExpect(content().string(MessagesExceptions.INVALID_MAIL));
    }

    /**
     * TODO. Parece que NO tiene sentido modificar el usuario como un todo,
     * porque la password no se serializa. Cuando se obtiene un User del
     * Servidor, la pass viene en null Cuando se modifica algo del usuario (ej
     * el 'userName' en este caso) se manda al servidor la pass en null y como
     * tiene la anotacion @Valid se aplica la validacion JSP-303 antes de entrar
     * al servicio. La validacion falla y Spring tira
     * MethodArgumentNotValidException. Como no hay ningun ExceptionHandler
     * asociado a esta excepcion termina saliendo por InternalServerError
     */
    @Test
    public void tryToUpdateUserAndFail() throws Exception {
        User user = findFirstUser();

        user.setName("pepito3@gmail.com");
        String json = convertToJson(user);

        this.mockMvc.perform(put("/users")
                            .content(json))
                            .andExpect(status().isInternalServerError());
    }

    /**
     * TODO revisar codigo de respuesta ... No tendría que ir un 200 ? ACCEPTED
     * = 202 The client’s request can’t or won’t be handled in real time. It
     * will be processed later. The request looks valid, but it might turn out
     * to have problems when it’s finally processed. This is an appropriate
     * response when a request triggers an asynchronous action...
     * @throws Exception
     */
    @Test
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users/{id}", secondId))
                            .andExpect(status().isAccepted());
    }

    // *******************************************************************************
    // * HELPERS VARIOS
    // *******************************************************************************
    private String findAllJson() throws Exception, UnsupportedEncodingException {
        MvcResult mvcResult = this.mockMvc.perform(get("/users")
                                                   .accept(jsonType))
                                                   .andExpect(status().isOk())
                                                   .andReturn();
        
        return mvcResult.getResponse().getContentAsString();
    }

    private User findFirstUser() throws Exception {
        String json = findAllJson();
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(json, new TypeReference<List<User>>() {} );

        return users.get(0);
    }

    private String convertToJson(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }

}
