package myApp;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.http.HTTPException;

import client.ClientBookShop;



@Path("/books")
public class BookResource {
	
	private final static Logger LOG = Logger.getLogger(BookResource.class.getName());

	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	public List<Book> getBooks(@QueryParam("userId") String userId, @HeaderParam("Authentication") String hMACClient ) {
		LOG.info("-----------------line 32: get all books" + userId + " "  + hMACClient );
		String secret = UserDao.instance.getUser(userId).getUserSecret();
		SecretKey secretKey = ClientBookShop.getBase64DecodedKey(secret);
		String hMACServer  = ClientBookShop.getBase64EncodedHMAC(userId, secretKey);
		
		if(hMACServer.equals(hMACClient)) {
			return BookDao.instance.getBooks();
		}else {
			 throw new HTTPException(HttpServletResponse.SC_FORBIDDEN);
		}		
		
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	@Path("{bookName}")
	public Book getBook(@PathParam("bookName") String bookName, @QueryParam("userId") String userId, @HeaderParam("Authentication") String hMACClient ) {
		LOG.info("-----------------line 40: get book" + bookName);
		String secret = UserDao.instance.getUser(userId).getUserSecret();
		SecretKey secretKey = ClientBookShop.getBase64DecodedKey(secret);
		String hMACServer  = ClientBookShop.getBase64EncodedHMAC(bookName+userId, secretKey);
		
		if(hMACServer.equals(hMACClient)) {
			return BookDao.instance.getBook(bookName);
		}else {
			 throw new HTTPException(HttpServletResponse.SC_FORBIDDEN);
		}		
		
		
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Book postBook(@FormParam("bookName") String bookName, @FormParam("bookAuthor") String bookAuthor,	@FormParam("bookPrice") String bookPrice, @QueryParam("userId") String userId, @HeaderParam("Authentication") String hMACClient, @Context HttpServletResponse servletResponse) throws Exception {
		LOG.info("-----------------line 71: create " + bookName);
		Book book = new Book();
		if(bookName==null){
			bookName="Default";
		}
		if(bookAuthor==null){
			bookAuthor="Default";
		}
		book.setBookName(bookName);
		book.setBookAuthor(bookAuthor);
		Double bookPriceDouble = 0.0; 
		System.out.println(bookPrice);
		if(bookPrice != null) {
		    bookPriceDouble = Double.parseDouble(bookPrice);
		}
		book.setBookPrice(bookPriceDouble);
		
		String secret = UserDao.instance.getUser(userId).getUserSecret();
		SecretKey secretKey = ClientBookShop.getBase64DecodedKey(secret);
		String hMACServer  = ClientBookShop.getBase64EncodedHMAC(bookName+bookAuthor+bookPrice+userId, secretKey);

		java.util.Date date= new java.util.Date();
        Timestamp timeSt = new Timestamp(date.getTime());
		String requestType = "POST";
		Record newRecord = new Record();
		newRecord.setTimeSt(timeSt);
		newRecord.setRequestType(requestType);
		newRecord.setUserId(userId);
		
		
		if(hMACServer.equals(hMACClient) ) {
			LOG.info("----------line95 : ");
			String accessStatus = "Allowed";
			newRecord.setAccessStatus(accessStatus);
			RecordDao.instance.create(newRecord);
			
			return BookDao.instance.create(book);
			//record this
		}else {
			LOG.info("----------line99 : ");
			
			String accessStatus = "Denied";
			newRecord.setAccessStatus(accessStatus);
			RecordDao.instance.create(newRecord);
			
			return null;
		}	
		
	}

	@DELETE	
	@Path("{bookName}")
	public void deleteBook(@PathParam("bookName") String bookName,@QueryParam("userId") String userId, @HeaderParam("Authentication") String hMACClient) throws Exception {
		LOG.info("-------------line 70: delete " + bookName);
		String secret = UserDao.instance.getUser(userId).getUserSecret();
		SecretKey secretKey = ClientBookShop.getBase64DecodedKey(secret);
		String hMACServer  = ClientBookShop.getBase64EncodedHMAC(bookName+userId, secretKey);
		
		java.util.Date date= new java.util.Date();
        Timestamp timeSt = new Timestamp(date.getTime());
		String requestType = "DELETE";
		Record newRecord = new Record();
		newRecord.setTimeSt(timeSt);
		newRecord.setRequestType(requestType);
		newRecord.setUserId(userId);
		
		if(hMACServer.equals(hMACClient) ) {
			LOG.info("--------line 114: ");
			
			String accessStatus = "Allowed";
			newRecord.setAccessStatus(accessStatus);
			RecordDao.instance.create(newRecord);
			
			BookDao.instance.delete(bookName);
			//record this
		}else {
			LOG.info("--------line 118: ");
			String accessStatus = "Denied";
			newRecord.setAccessStatus(accessStatus);
			RecordDao.instance.create(newRecord);
			
			throw new HTTPException(HttpServletResponse.SC_FORBIDDEN);
		}	
		
		
		
	}

	/*@PUT	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{bookName}")
	public void putBook(@PathParam("bookName") String bookName,@FormParam("bookAuthor") String bookAuthor,@FormParam("bookPrice") String bookPrice,@Context HttpServletResponse servletResponse) throws IOException {
		LOG.info("----------------line 80: update " + bookName);
		Book book = new Book();
		book.setBookAuthor("bookName");
		book.setBookAuthor("bookAuthor");
		book.setBookPrice(Double.parseDouble(bookPrice));
		
		BookDao.instance.create(book);
	}*/
}