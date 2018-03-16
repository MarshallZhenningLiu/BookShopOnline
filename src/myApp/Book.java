package myApp;

import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Book")
// Optional
@XmlType(propOrder = { "bookName", "bookAuthor", "bookPrice" })
public class Book {
  
	private final static Logger LOG = Logger.getLogger(Book.class.getName());
    private String bookName;
    private String bookAuthor;
    private double bookPrice;
    
	public String getBookName() {		
		return bookName;
	}
	public void setBookName(String bookName) {		
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}

    
 
}
