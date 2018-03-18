package myApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import javax.servlet.http.HttpServletResponse;



@Path("/users")
public class UserResource {
	
	private final static Logger LOG = Logger.getLogger(UserResource.class.getName());
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	public List<User> getUsers() {
		LOG.info("------------------line 36: get all users");
		return UserDao.instance.getUsers();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	@Path("{userId}")
	public User getUser(@PathParam("userId") String userId) {
		LOG.info("------------------line 45: get " + userId);
		return UserDao.instance.getUser(userId);
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML )
	public void postUser(@FormParam("userId") String userId,@Context HttpServletResponse response) throws IOException {
		
		LOG.info("------------------line 55: create " + userId);
		User user = new User();
		user.setUserId(userId);
		
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}
		
	    SecretKey sk = kg.generateKey();
	    String encodedKey = DatatypeConverter.printBase64Binary(sk.getEncoded());
	    LOG.info("------------------line 65: encodedKey is: " + encodedKey); 

		user.setUserSecret(encodedKey);	
		UserDao.instance.create(user);
		 LOG.info("------------------line 69: user created "); 
		 		
		response.sendRedirect("http://localhost:8080/BookShopOnline/rest/users/"+userId);
		
		 
		/* FileWriter fWriter = null;
		 BufferedWriter writer = null;
		 try {
		     fWriter = new FileWriter("../register.html");
		     writer = new BufferedWriter(fWriter);
		     writer.write("<span>This iss your html content here</span>");		    
		     writer.close(); //make sure you close the writer object 
		     LOG.info("------------------line 81: write to file successfully "); 
		 } catch (Exception e) {
			 LOG.info("------------------line 83: write to file UN-successfully "); 
		 }	 
		 StringBuilder contentBuilder = new StringBuilder();
		 try {
		     BufferedReader in = new BufferedReader(new FileReader("../register.html"));
		     String str;
		     while ((str = in.readLine()) != null) {
		         contentBuilder.append(str);
		     }
		     in.close();
		 } catch (IOException e) {
		 }
		 String content = contentBuilder.toString();
		 LOG.info("------------------line 100:  " + content); 
		 servletResponse.setStatus(201);
		 servletResponse.setHeader(userId, encodedKey);
		//servletResponse.sendRedirect("../register.html");
*/	
		
	}
	

	/*@DELETE
	@Produces(MediaType.TEXT_HTML)
	@Path("{userId}")
	public void deleteUser(@PathParam("userId") String userId) throws IOException {
		LOG.info("-------------------deleteUser line 64: " + userId);

		UserDao.instance.delete(userId);
	}*/

	/*
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{userId}")
	public void putUser(@PathParam("userId") String userId,
			@FormParam("userSecret") String userSecret,
			@Context HttpServletResponse servletResponse) throws IOException {
	
		LOG.info("---------------------putUser line 77: " + userId);
		User user = new User();
		user.setUserId(userId);
		user.setUserSecret(userSecret);
		
		UserDao.instance.create(user);
	}*/
}