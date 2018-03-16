package myApp;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
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



@Path("/books")
public class BookResource {
	
	private final static Logger LOG = Logger.getLogger(BookResource.class.getName());

	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	public List<Book> getBooks() {
		LOG.info("-----------------line 32: get all books");
		return BookDao.instance.getBooks();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	@Path("{bookName}")
	public Book getBook(@PathParam("bookName") String bookName) {
		LOG.info("-----------------line 40: get book" + bookName);
		return BookDao.instance.getBook(bookName);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postBook(@FormParam("bookName") String bookName, @FormParam("bookAuthor") String bookAuthor,	@FormParam("bookPrice") String bookPrice,@Context HttpServletResponse servletResponse) throws IOException {
		LOG.info("-----------------line 45: create " + bookName);

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
			System.out.println(bookPrice);
		    bookPriceDouble = Double.parseDouble(bookPrice);
		    System.out.println(bookPriceDouble);
		}
		book.setBookPrice(bookPriceDouble);
		
		BookDao.instance.create(book);
	}

	@DELETE	
	@Path("{bookName}")
	public void deleteBook(@PathParam("bookName") String bookName) throws IOException {
		LOG.info("-------------line 70: delete " + bookName);
		BookDao.instance.delete(bookName);
	}

	@PUT	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{bookName}")
	public void putBook(@PathParam("bookName") String bookName,@FormParam("bookAuthor") String bookAuthor,@FormParam("bookPrice") String bookPrice,@Context HttpServletResponse servletResponse) throws IOException {
		LOG.info("----------------line 80: update " + bookName);
		Book book = new Book();
		book.setBookAuthor("bookName");
		book.setBookAuthor("bookAuthor");
		book.setBookPrice(Double.parseDouble(bookPrice));
		
		BookDao.instance.create(book);
	}
}