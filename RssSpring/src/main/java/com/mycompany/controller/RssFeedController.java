package com.mycompany.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.rss.FeedItemRO;
import com.mycompany.rss.RssFeedService;

// a controller through which the client side calls the app.
@Controller
public class RssFeedController {
	
	//attaching the service
	@Autowired
	private RssFeedService rssFeedService;
	
	// a method that asks for the feed data and returns a list of representation objects through the service
	@ResponseBody
	@RequestMapping(value = "/feed", method = {RequestMethod.GET})
	public List<FeedItemRO> getFeed( HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		return rssFeedService.getFeed();
	}

}


