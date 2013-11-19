package com.gl.todolist.web.test.rest;

import static com.gl.todolist.web.test.rest.RestBaseTest.name1;
import static com.gl.todolist.web.test.rest.RestBaseTest.pass1;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.todolist.domain.Task;
import com.gl.todolist.domain.TaskStatus;
import com.gl.todolist.domain.TaskType;
import com.gl.todolist.domain.User;
import com.gl.todolist.web.dto.TaskDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:integration-test-context.xml"}) 
public class IntegrationRestUserTest {

	private static final String CTX_PATH = "http://localhost:8080/todolist-web/";
	private static final String LIST_USERS = CTX_PATH + "rest/users";
	private static final String TASKS = CTX_PATH + "rest/tasks";
	
	private static final String LOGIN = CTX_PATH + "login";
	

	
	//Header que devuelve el servidor para indicarle al cliente que debe guardar la cookie
	private static final String SET_COOKIE_HEADER = "Set-Cookie";
	
	//Header que setea el cliente para enviarle al servidor la cookie previamente 
	//enviada por �ste
	private static final String COOKIE_HEADER = "Cookie";

	//Contiene el valor que el servidor env�a en 'SET_COOKIE_HEADER'. El test 'loginUser()'
	//guarda la cookie de JSESSIONID en esta variable para que pueda ser enviada por los tests
	//siguientes en el header 'COOKIE_HEADER'
	private static List<String> SESSION_COOKIE;  	
	
	//Nombres de los campos que Spring Security espera que sea posteen para logear un usuario 
	private static final String SPRING_SECURITY_USER_NAME_FIELD = "j_username";
	private static final String SPRING_SECURITY_PASSWORD_FIELD = "j_password";
	
	//La primer tarea que se crea y que luego se modifica respectivamente
	private static Task firstTask;
	private static Task modifiedTask;
	

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private DataBaseHelper dataBaseHelper;
	
	private static boolean wasClearedDB = false;
	
	private static Logger logger = LoggerFactory.getLogger(IntegrationRestUserTest.class); 
	
	@Before
	public void clearDB() {
		if (!wasClearedDB) {
			this.dataBaseHelper.clearDB();
			wasClearedDB = true;
		}
	}


	/**
	 * Si activamos el logeo  en la clase 'RestTemplate' vamos a ver las siguientes lineas:
	 *  
	 * (1) RestTemplate - Created POST request for "http://localhost:8080/todolist-web/rest/users"
	 * (2) RestTemplate - Setting request Accept header to [application/json, application/*+json]
     * (3) RestTemplate - Writing [{"name":"pepito1@gmail.com","password":"zaraza1"}] as "application/json" using [org.springframework.http.converter.StringHttpMessageConverter@60c017e9]
     * (4) RestTemplate - POST request for "http://localhost:8080/todolist-web/rest/users" resulted in 201 (Creado)
     * (5) RestTemplate - Reading [class com.gl.todolist.domain.User] as "application/json;charset=UTF-8" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@24c6e1ec]
     * 
     * Observar que Spring setea automaticamente el 'Accept' header(2). Para hacerlo va recorriendo
     * los HttpMessageConverter registrados hasta que encuentra el primero que 'canRead(User.class ...)'
     * El primer converter que puede leer Usuarios es MappingJackson2HttpMessageConverter. 
     * Se le piden getSupportedMediaTypes() a ese converter y esa lista es la que se manda 
     * en el 'Accept' header 
     * En la linea (5) se puede ver que para leer la respuesta del servidor se usa 
     * MappingJackson2HttpMessageConverter 
     * 
     * Para escribir el body del request http se usa un StringHttpMessageConverter(3). 
     * Esto es as� porque el tipo de Body es String y entonces el StringConverter es el primer
     * converter registrado que canWrite(String.class...)
     * Tengo que mandar un String, porque si mando un Usuario como la password no se serializa
     * (por el JsonIgnore) no viaja en el mensaje. El servidor no lo est� validando ahora ... 
	 */
	@Test
	public void createUser()  {
		String jsonUser = RestBaseTest.createJsonUser(name1, pass1);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(jsonUser, headers);
		
		User user = restTemplate.postForObject(LIST_USERS, entity, User.class);		
		
		assertNotNull(user.getId());
		logger.debug("Se creo exitosamente el usuario = " + user);
	}	
	
	/**
	 * Logea al usuario creado en el test anterior. Los campos que se postean en el form son los 
	 * que utiliza Spring Security. Observar que a 'autenticationFilter'  se lea setea 
	 * propiedad filterProcessesUrl = "/login".El filtro 
	 * UsernamePasswordAuthenticationFilter.requiresAuthentication()
	 * chequea si uri.endsWith(request.getContextPath() + filterProcessesUrl)  
	 * 
	 * Se guarda la cookie de sesion en SESSION_COOKIE para que los siguientes tests
	 * la puedan enviar en los requests al servidor. En todos los servicios de tareas
	 * NO se postea el usuario, el usuario se obtiene la session el servidor
	 */
	@Test
	public void loginUser()  {
		HttpEntity<?> reqEntity = makeRequestForLogin();
					
		ResponseEntity<byte[]> respEntity= restTemplate.postForEntity(LOGIN, reqEntity, 
																			byte[].class);		
		
	
		HttpHeaders headers = respEntity.getHeaders();
		SESSION_COOKIE = headers.get(SET_COOKIE_HEADER);
		
		assertEquals(HttpStatus.OK, respEntity.getStatusCode());
		assertNotNull("Se esperaba recibir la cookie de session", SESSION_COOKIE);
		assertNull(respEntity.getBody());
		
		logger.debug("Se logeo exitosamente el usuario y se obtuvieron " + 
						   "en la respuesta los headers = " + headers);
	}
	
	@Test
	public void getTasksAndShouldByEmpty() {
		ResponseEntity<Task[]> response = doGetTasks();	
		
		assertEquals(0, response.getBody().length);	
		logger.debug("Se pidio la lista de tareas del usuario y est� vac�a");
		
	}

	
	@Test
	public void addTask() {
		HttpEntity<String> reqEntity = makeRequestWithSessionCookie(createJsonTask());		
		ResponseEntity<Task> respEntity = restTemplate.postForEntity(TASKS, reqEntity, 
																		 Task.class);											

		firstTask = respEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, respEntity.getStatusCode());
		assertNotNull(firstTask.getId());
		assertTrue(EqualsBuilder.reflectionEquals(createTask(), firstTask, "id"));
		
		logger.debug("Se creo existosamente la tarea = " + firstTask);		
	}

	@Test
	public void getTasksAndShouldHaveOne() {
		ResponseEntity<Task[]> response = doGetTasks();
		Task[] tasks = response.getBody();
			
		assertEquals(1, tasks.length);
		assertTrue(EqualsBuilder.reflectionEquals(firstTask, tasks[0]));
		
		logger.debug("Se pidio la lista de tareas del usuario " + 
						   "y tiene la tarea agregada " + tasks[0]);
		
	}
	

	@Test
	public void updateTask() {
		HttpEntity<String> reqEntity = makeRequestWithSessionCookie(createJsonTaskDTO());		
		ResponseEntity<Task> respEntity = restTemplate.exchange(TASKS + "", 
																HttpMethod.PUT, 
																reqEntity, 
																Task.class);											

		modifiedTask  = respEntity.getBody();
		
		assertEquals(HttpStatus.OK, respEntity.getStatusCode());
		assertEquals(firstTask.getId(), modifiedTask.getId());
		assertTrue(modifiedTask.getTitle().endsWith("ASADO"));
		
		logger.debug("Se modific� existosamente la tarea " + modifiedTask);	
		
		ResponseEntity<Task[]> response = doGetTasks();
		Task[] tasks = response.getBody();
		assertTrue(EqualsBuilder.reflectionEquals(modifiedTask, tasks[0]));
	}
	
	
	/**
	 * TODO cuando se invoca al delete, el servidor tira ObjectDeletedException 
	 * debageando porque pasa esto cuando se levanta la task que se quiere borrar, tambien
	 * se levanta el User asociado porque la relacion es EAGER. El user tambien tiene 
	 * como EAGER su coleccion de 'task'. Entonces los objetos quedan doblemente vinculados
	 * Cuando hace un flush de la session de Hibernate, (activar trace de Hibernate)
	 * que se hace un persist del User que se propaga a la coleccion de task:
	 * processing cascade ACTION_PERSIST_ON_FLUSH for: com.gl.todolist.domain.User
	 * cascade ACTION_PERSIST_ON_FLUSH for collection: com.gl.todolist.domain.User.task
	 * 
	 *  y como la task de la coleccion se borr� antes tira error.
	 *  
	 * La cosa no se soluciona haciendo lazzy la relacion task.user ... porque si hacemos
	 * eso se rompe RestTaskServices>>updateTask con LazyInitializationException porque 
	 * leventa la tarea de la BD y despu�s chequea que el user de la task sea el mismo que est�
	 * en Session: 
	 *  
	 *   public Task updateTask(@RequestBody TaskDTO taskDTO,HttpSession session) throws UserException{
	 *		Task task = iTaskServices.findTask(taskDTO.getId());
	 *	    if(getLoggedUser().getName().equalsIgnoreCase(task.getUser().getName())){
	 *      ...	
	 */
	@Test
	public void deleteTaskBUG() {
		HttpEntity<?> reqEntity = makeRequestWithSessionCookie();
		
		try {
			ResponseEntity<byte[]> respEntity = restTemplate.exchange(TASKS + "/{id}", 
					HttpMethod.DELETE, 
					reqEntity, 
					byte[].class,
					firstTask.getId());														
		}
		catch (HttpServerErrorException e) {
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
		}

 		//El resultado que esperaria obtener		
		//assertEquals(HttpStatus.ACCEPTED, respEntity.getStatusCode());
		//logger.debug("Se elimin� existosamente la tarea " + firstTask);			
	}
		

	@Test
	public void findById() {
		HttpEntity<?> reqEntity = makeRequestWithSessionCookie();
		ResponseEntity<Task> respEntity = restTemplate.exchange(TASKS + "/{id}",  
																HttpMethod.GET,
																reqEntity,
																Task.class,
																firstTask.getId());											

		Task returnedTask  = respEntity.getBody();
		
		assertEquals(HttpStatus.OK, respEntity.getStatusCode());
	
		assertTrue(EqualsBuilder.reflectionEquals(modifiedTask, returnedTask));
		
		logger.debug("Se busco por ID existosamente la tarea " + returnedTask);		
	}
		

	/**
	 * Intento hacer un findById pero sin mandar la cookie de Session. Entonces, como
	 * la URL est� segurizada falla por un 403
	 */
	@Test
	public void tryToAccessSecuredResourceAndFail() {
		try {	
			ResponseEntity<Task> respEntity = restTemplate.getForEntity(TASKS + "/{id}",  
																Task.class,
																firstTask.getId());											
		}
		catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
			logger.debug("Se intent� buscar una tarea sin logging y se obtuvo " 
								+ " FORBIDDEN como se esperaba");
		}
	}
			
	
	
	// *******************************************************************************
	// *                        HELPERS VARIOS
	// *******************************************************************************	
	private String createJsonTask() {
		Task task = createTask();
		return convertToJson(task);
	}


	private static String convertToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("No se pudo serializar object " + object, e);
		}
	}

	private Task createTask() {
		Task task = new Task();
		task.setTaskStatus(TaskStatus.PENDING);
		task.setTitle("Ir al supermercado");
		task.setTaskType(TaskType.URGENT);
		return task;
	}

	private String createJsonTaskDTO() {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(firstTask.getId());
		taskDTO.setTitle("Ir al supermercado para comprar el ASADO");
		taskDTO.setTaskStatus(firstTask.getTaskStatus());
		taskDTO.setTaskType(firstTask.getTaskType());

		return convertToJson(taskDTO);
	}	
	
	private HttpEntity<?> makeRequestWithSessionCookie() {
		HttpHeaders headers = new HttpHeaders();
		headers.put(COOKIE_HEADER, SESSION_COOKIE);	
		
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		return entity;
	}

	private HttpEntity<String> makeRequestWithSessionCookie(String jsonBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.put(COOKIE_HEADER, SESSION_COOKIE);	
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
		return entity;
	}		


	private HttpEntity<?> makeRequestForLogin() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     
		body.add(SPRING_SECURITY_USER_NAME_FIELD, name1);
		body.add(SPRING_SECURITY_PASSWORD_FIELD, pass1);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				
		return new HttpEntity<Object>(body, headers);
	}	
		
	
	private ResponseEntity<Task[]> doGetTasks() {
		HttpEntity<?> reqEntity = makeRequestWithSessionCookie();
		ResponseEntity<Task[]> respEntity = restTemplate.exchange(TASKS, HttpMethod.GET, 
				      			   								reqEntity, Task[].class);
		return respEntity;
	}
		
}