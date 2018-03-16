package client;

import java.net.URI;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

public class ClientBookShop {
  private static Scanner sc = new Scanner(System.in);
  static ClientConfig config;
  static Client client;
  static WebResource service;
  
  public static void main(String[] args) {
	
	for(String s: args){
		System.out.println(s);
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
                getBooks();
                break;
            case 2:
                getBookByName();
                break;
            case 3:
                createBook();
                break;
            case 4:
                deleteBookByName();
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
    
  

    private static void deleteBookByName() {
    	String name;
		do{
			System.out.print("Enter book name:");
			Scanner sc = new Scanner(System.in);
			name=sc.nextLine();
		}while(name.length()==0);
		try{
			service.path("rest").path("books").path(name).delete();

		    }catch(Exception e){
		    	System.out.println("!!! --- Error with deleting this book. ---");
		    }		
		
	}
	




	private static void createBook() {
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
	    try{
	    service.path("rest").path("books").type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
	    System.out.println(name + " is created.");
	    }catch(Exception e){
	    	System.out.println("!!! --- Error with creating this book. ---");
	    }
	}




	private static void getBookByName() {
		String name;
		do{
			System.out.print("Enter book name:");
			Scanner sc = new Scanner(System.in);
			name=sc.nextLine();
		}while(name.length()==0);
		
		try{
		System.out.println(service.path("rest").path("books").path(name).accept(MediaType.APPLICATION_JSON).get(String.class));
		}catch(Exception e){
			System.out.println("!!! --- Error with finding this book. ---");
		}
	}




	//------------------- GET   
    public static void getBooks(){    
    	System.out.println(service.path("rest").path("books").accept(MediaType.APPLICATION_JSON).get(String.class));
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
