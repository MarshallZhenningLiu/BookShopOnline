package myApp;

import java.util.List;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;

import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.http.HTTPException;
import client.ClientBookShop;
import javax.servlet.http.HttpServletResponse;

@Path("/records")
public class RecordResource {
	
	private final static Logger LOG = Logger.getLogger(RecordResource.class.getName());	


	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	public List<Record> getRecords(@QueryParam("userId") String userId, @HeaderParam("Authentication") String hMACClient ) {
		LOG.info("-----------------line 47: get all records" + userId + " "  + hMACClient );
		String secret = UserDao.instance.getUser(userId).getUserSecret();
		SecretKey secretKey = ClientBookShop.getBase64DecodedKey(secret);
		String hMACServer  = ClientBookShop.getBase64EncodedHMAC(userId, secretKey);
		
		if(hMACServer.equals(hMACClient)) {
			return RecordDao.instance.getRecords();
		}else {
			 throw new HTTPException(HttpServletResponse.SC_FORBIDDEN);
		}		
		
	}

}