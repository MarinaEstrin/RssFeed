package Rss.Rss;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;  

//the servlet that exposes the API for the client
public class RssServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException {
		
		/* create a list of representation objects through the Service -
		  getInstance() a static method to get/create the class (singleton) and getFeed() to receive the data */
		List<FeedItemRO> feedItemROs = RssFeedServiceImpl.getInstance().getFeed();
		
		// adding to header to allow access to the app from any page (CORS)
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Credentials", "true");
		
		//setting the content type
		res.setContentType("application/json");
		
		//get the stream to write the data
		PrintWriter pw=res.getWriter();  
		
		// turning the json data feed to string using Gson
		Gson gson = new Gson();
		String jsonInString = gson.toJson(feedItemROs);
		
		//writing html in the stream  
		pw.println(jsonInString);  
		
		//closing the stream
		pw.close();
	} 
			
}

