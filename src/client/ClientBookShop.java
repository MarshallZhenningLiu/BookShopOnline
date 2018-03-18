package client;

import java.net.URI;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.ws.http.HTTPException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import myApp.BookResource;

public class ClientBookShop {
  private final static Logger LOG = Logger.getLogger(ClientBookShop.class.getName());
  private static Scanner sc = new Scanner(System.in);
  static ClientConfig config;
  static Client client;
  static WebResource service;
  
  public static void main(String[] args) {
	
	String user=null, secret=null;
	if(args!=null && args.length>1) {
		user=args[0];
		secret=args[1];
		LOG.info("-----------------line 31: " + user + " " + secret);
	}else {
		System.out.println("!!! --- Error with user name or secret key ---");
	}
	
	
    config = new DefaultClientConfig();
    client = Client.create(config);
    service = client.resource(getBaseURI());
    
    //-----------------------------------------------selection menu
    
    String userContinue = "y";
    System.out.println("------ Book Shop Online ------");
    while (userContinue.equalsIgnoreCase("y")) {
        switch (userChoice()) { 
            case 1:
                getBooks(user, secret);
                break;
            case 2:
                getBookByName(user,secret);
                break;
            case 3:
                createBook(user,secret);
                break;
            case 4:
                deleteBookByName(user,secret);
                break;           
            case 5:
                System.out.println("Exiting...");
                userContinue = "n";
                break;
            default:
                System.out.println("Unknown entry : ");
                break;
        }
    }
  }

 //--------------------------------------------------------------
    
  

    private static void deleteBookByName(String userId, String secret) {
	    	String name;
			do{
				System.out.print("Enter book name:");
				Scanner sc = new Scanner(System.in);
				name=sc.nextLine();
			}while(name.length()==0);
			
		
			SecretKey secretKey = getBase64DecodedKey(secret);
	    	String hMAC  = getBase64EncodedHMAC(name+userId, secretKey); 
		    
			try{
				service.path("rest").path("books").path(name).queryParam("userId", userId).header("Authentication", hMAC).delete();	
				System.out.println(name + " is deleted.");
			}catch(Exception e){
			    	System.out.println("!!! --- Error with deleting this book. ---");
			}		
		
	}
	




	private static void createBook(String userId, String secret) {
		String name,author;
		Double price=0.0;
		do{
			System.out.print("Enter book name:");
			Scanner sc = new Scanner(System.in);
			name=sc.nextLine();
		}while(name.length()==0);
		do{
			System.out.print("Enter book author:");
			Scanner sc = new Scanner(System.in);
			author=sc.nextLine();
		}while(author.length()==0);
		do{
			System.out.print("Enter book price:");
			Scanner sc = new Scanner(System.in);
			
			try{
				price=sc.nextDouble();
			}catch(Exception e){
				System.out.println("incorrect price entered.");
				price=-1.0;
			}
		}while(price<0);
	
		Form form = new Form();
	    form.add("bookName", name);
	    form.add("bookAuthor", author);
	    form.add("bookPrice", Double.toString(price));
	    
	    SecretKey secretKey = getBase64DecodedKey(secret);
    	String hMAC  = getBase64EncodedHMAC(name+author+price+userId, secretKey); 
	    try{
	    	service.path("rest").path("books").queryParam("userId", userId).header("Authentication", hMAC).type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
	    	System.out.println(name + " is created.");
	    }catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println("!!! --- Error with creating this book. ---");
	    }
	}




	private static void getBookByName(String userId, String secret) {
			String name;
			do{
				System.out.print("Enter book name:");
				Scanner sc = new Scanner(System.in);
				name=sc.nextLine();
			}while(name.length()==0);
			
			SecretKey secretKey = getBase64DecodedKey(secret);
	    	String hMAC  = getBase64EncodedHMAC(name+userId, secretKey); 
			try {
				System.out.println(service.path("rest").path("books").path(name).queryParam("userId", userId).header("Authentication", hMAC).accept(MediaType.APPLICATION_JSON).get(String.class));
			
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("!!! --- Error with finding this book. ---");
			}
		
		

	}

	//------------------- GET   
    public static void getBooks(String userId, String secret){    	
    		SecretKey secretKey = getBase64DecodedKey(secret);
    		String hMAC  = getBase64EncodedHMAC(userId, secretKey); 
    	
			try {
				System.out.println(service.path("rest").path("books").queryParam("userId", userId).header("Authentication", hMAC).accept(MediaType.APPLICATION_JSON).get(String.class));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("!!! --- Error with finding books. ---");
			} 		
    }
    
    //------------------------------------------------------------------------------------------------
    public static SecretKey getBase64DecodedKey(String secret) {
    	byte[] decodedKey = Base64.getDecoder().decode(secret);
    	SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    	return secretKey;
   
    }
    
    
    public static String getBase64EncodedHMAC(String message, SecretKey secretKey) {
    	try {			
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKey);
			byte[] hmac = mac.doFinal(message.getBytes());
			String encodedHmac  = Base64.getEncoder().encodeToString(hmac);
			LOG.info("line 177: encoded hamc is " + encodedHmac);
			return encodedHmac;
			
		} catch (Exception e) {			
			e.printStackTrace();
		}    	
    	return null;    	
    }

//--------------------------------------------------------------------------------------------  
  public static int userChoice() {
	  System.out.println();
      System.out.println("What do you want to do ?");
      System.out.println();
      System.out.println("1. Get all books");
      System.out.println("2. Get a book by bookname");
      System.out.println("3. Create a book");
      System.out.println("4. Delete a book by bookname");
      System.out.println("5. Exit");
      System.out.print("Enter choice --> ");
      Scanner sc = new Scanner(System.in);
      return sc.nextInt();
  }
  
  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8080/BookShopOnline").build();
  }
}
