/**
 * 
 */
package nb.server.dispacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

/**
 * @author Vincentas
 * 
 */
@Singleton
public class ReportHandler extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		resp.setStatus(HttpServletResponse.SC_OK);
		
		Map<String, String> requestData = new HashMap<String, String>();
		String type = req.getParameter("type");
		if (type == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if (type.equalsIgnoreCase("pubCorrect")) {
			requestData.put("pubId", req.getParameter("pubId"));
			requestData.put("reason", req.getParameter("reason"));
			requestData.put("message", req.getParameter("message"));
		} else if (type.equalsIgnoreCase("newPub")) {
			requestData.put("message", req.getParameter("message"));
			requestData.put("isNear", req.getParameter("isNear"));
			requestData.put("location_latitude", req.getParameter("location_latitude"));
			requestData.put("location_longitude", req.getParameter("location_longitude"));
			requestData.put("location_accuracy", req.getParameter("location_accuracy"));
			requestData.put("location_provider", req.getParameter("location_provider"));
		} else if (type.equalsIgnoreCase("pubBrandInfo")) {
			requestData.put("message", req.getParameter("message"));
			requestData.put("pubId", req.getParameter("pubId"));
			requestData.put("brandId", req.getParameter("brandId"));
			requestData.put("status", req.getParameter("status"));
			requestData.put("location_latitude", req.getParameter("location_latitude"));
			requestData.put("location_longitude", req.getParameter("location_longitude"));
			requestData.put("location_accuracy", req.getParameter("location_accuracy"));
			requestData.put("location_provider", req.getParameter("location_provider"));
		} else {
			requestData = new HashMap<String, String>(req.getParameterMap());
		}
		
		StringBuilder builder = new StringBuilder(type + "\r\n");
		for (Map.Entry<String, String> entry : requestData.entrySet()) {			
			builder.append(entry.getKey() + " = " + entry.getValue() + "\r\n");
		}
		String msgBody = builder.toString();
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("report@alausradaras.appspotmail.com", "Alaus radaro reporteris"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("vincnetas@gmail.com", "Vincentas Vienozinskis"));
			msg.setSubject("[Alaus reporteris]");
			msg.setText(msgBody);
			Transport.send(msg);
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		}
		
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
