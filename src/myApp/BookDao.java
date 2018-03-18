package myApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import java.sql.*;

public enum BookDao {
  instance;
	
	/*	String url = "jdbc:mysql://localhost:3307/";
	String dbName = "BookShop";
	String userName = "root";
	String password = "admin";*/	
	
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "BookShop";
	String userName = "root";
	String password = "Change01";
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;


  private final static Logger LOG = Logger.getLogger(BookDao.class.getName());
  private Map<String, Book> booksMap = new HashMap<String, Book>();

  public List<Book> getBooks() {
    List<Book> books = new ArrayList<Book>();  
    
    try {
    	Class.forName("com.mysql.jdbc.Driver");
    	con=DriverManager.getConnection(url+dbName, userName, password);
    	stmt = con.createStatement();
    	rs = stmt.executeQuery("select * from Book");
    	while(rs.next()) {
    		Book book = new Book();
    		book.setBookName(rs.getString("bookName"));
    		book.setBookAuthor(rs.getString("bookAuthor"));
    		book.setBookPrice(Double.parseDouble( rs.getString("bookPrice") ) );
    		books.add(book);		
    	}
    }catch(Exception e) {
    	e.printStackTrace();
    }finally {
    	if(rs!=null) {	try{rs.close();}catch(Exception e) {e.printStackTrace(); }}
    	if(stmt!=null) {	try{stmt.close();}catch(Exception e) {e.printStackTrace(); }}
    	if(con!=null) {	try{con.close();}catch(Exception e) {e.printStackTrace(); }}
    }   
   
    return books;
  }

  public Book getBook(String bookName) {	  
	  try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	con=DriverManager.getConnection(url+dbName, userName, password);
	    	stmt = con.createStatement();
	    	rs = stmt.executeQuery("select * from Book where bookName like  \"" + bookName +"\""  );
	    	while(rs.next()) {
	    		Book book = new Book();
	    		book.setBookName(rs.getString("bookName"));
	    		book.setBookAuthor(rs.getString("bookAuthor"));
	    		book.setBookPrice(Double.parseDouble( rs.getString("bookPrice") ));	    		
	    		return book;	    		
	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	if(rs!=null) {	try{rs.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(stmt!=null) {	try{stmt.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(con!=null) {	try{con.close();}catch(Exception e) {e.printStackTrace(); }}
	    }	  
	  
  return null;
 
  }

  public void create(Book book) {
	  LOG.info("--------create line45: " + book.getBookName());
	  
	  String bookName = book.getBookName();
	  LOG.info("--------create line45: " + bookName);
	  String bookAuthor = book.getBookAuthor();
	  LOG.info("--------create line45: " + bookAuthor);
	  Double bookPrice = book.getBookPrice();
	  LOG.info("--------create line45: " + bookPrice);
	  
	  try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	con=DriverManager.getConnection(url+dbName, userName, password);
	    	stmt = con.createStatement();	    	
	    	int row = stmt.executeUpdate("insert into book  values (\"" + bookName + "\" ,\"" + bookAuthor + "\"," + bookPrice + ")");    	
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	if(rs!=null) {	try{rs.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(stmt!=null) {	try{stmt.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(con!=null) {	try{con.close();}catch(Exception e) {e.printStackTrace(); }}
	    }
	  
	  
    booksMap.put(book.getBookName(), book);
  }

  public void delete(String bookName) {
	  try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	con=DriverManager.getConnection(url+dbName, userName, password);
	    	stmt = con.createStatement();	    	
	    	int row = stmt.executeUpdate("delete from book where bookName like \"" + bookName + "\"");    	
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	if(rs!=null) {	try{rs.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(stmt!=null) {	try{stmt.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(con!=null) {	try{con.close();}catch(Exception e) {e.printStackTrace(); }}
	    }

  }

}
