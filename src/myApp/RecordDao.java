package myApp;

import myApp.Record;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.sql.*;

public enum RecordDao {
  instance;

/*	String url = "jdbc:mysql://localhost:3307/";
	String dbName = "BookShop";
	String RecordName = "root";
	String password = "admin";*/	
	
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "BookShop";
	String userName = "root";
	String password = "Change01";
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
  private final static Logger LOG = Logger.getLogger(RecordDao.class.getName());
  private Map<Timestamp, Record> recordsMap = new HashMap<Timestamp, Record>();

  public List<Record> getRecords() {
    List<Record> Records = new ArrayList<Record>();    
    try {
    	Class.forName("com.mysql.jdbc.Driver");
    	con=DriverManager.getConnection(url+dbName,userName, password);
    	stmt = con.createStatement();
    	rs = stmt.executeQuery("select * from Record");
    	while(rs.next()) {
    		Record Record = new Record();
    		Record.setUserId(rs.getString("UserId"));
    		Record.setTimeSt(rs.getTimestamp("TimeSt"));
    		Record.setRequestType(rs.getString("RequestType"));
    		Record.setAccessStatus(rs.getString("AccessStatus"));
    		Records.add(Record);    		
    	}
    }catch(Exception e) {
    	e.printStackTrace();
    }finally {
    	if(rs!=null) {	try{rs.close();}catch(Exception e) {e.printStackTrace(); }}
    	if(stmt!=null) {	try{stmt.close();}catch(Exception e) {e.printStackTrace(); }}
    	if(con!=null) {	try{con.close();}catch(Exception e) {e.printStackTrace(); }}
    }
    
    return Records;
  }

  
  public Record create(Record record) {
	  String userId = record.getUserId();
	  String accessStatus = record.getAccessStatus();
	  String requestType = record.getRequestType();
	  Timestamp timeSt = record.getTimeSt();	 
	  
	  try {
	    	Class.forName("com.mysql.jdbc.Driver");
	    	con=DriverManager.getConnection(url+dbName, userName, password);
	    	stmt = con.createStatement();	    	
	    	//int row = stmt.executeUpdate(" insert into record(timeSt, userId, requestType, accessStatus) values (\"" + timeSt + "\" ,\"" + userId + "\",\"" + requestType + "\",\"" + accessStatus + ") ");    	
	    	int row = stmt.executeUpdate(" insert into record(timeSt, userId, requestType, accessStatus) values ('" + timeSt + "','" + userId + "','" + requestType + "','" + accessStatus + "')");   
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	if(rs!=null) {	try{rs.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(stmt!=null) {	try{stmt.close();}catch(Exception e) {e.printStackTrace(); }}
	    	if(con!=null) {	try{con.close();}catch(Exception e) {e.printStackTrace(); }}
	    }
	  
	  
    recordsMap.put(record.getTimeSt(), record);
    return record;
  }
  
  
  
 

}
