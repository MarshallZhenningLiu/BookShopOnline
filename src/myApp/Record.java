package myApp;


import java.sql.Timestamp;
import java.util.logging.Logger;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Record")
// Optional
@XmlType(propOrder = { "timeSt","userId","requestType","accessStatus"})
public class Record {
	private final static Logger LOG = Logger.getLogger(User.class.getName());
	
	private String userId;
    private String requestType;
    
	private Timestamp timeSt;
    private String accessStatus;
         
	
    public Timestamp getTimeSt() {
		return timeSt;
	}
	public void setTimeSt(Timestamp timeSt) {
		this.timeSt = timeSt;
	}
	
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
	}
	
 
}
