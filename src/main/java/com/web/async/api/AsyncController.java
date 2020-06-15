/**
 * 
 */
package com.web.async.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.async.model.User;
import com.web.async.service.GitHubLookupService;
import com.web.async.service.IExternalService;

/**
 * @author asiml
 *
 */
@RestController
@RequestMapping("/api/async")
public class AsyncController 
{
	private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);
	 
	@Autowired
	private IExternalService externalServ;
	
	@Autowired
	private GitHubLookupService lookUpServ;
	
	@GetMapping("/v1/details")
	public ResponseEntity<String> getExternalDetails()
	{
		System.out.println(" Before Calling external service "+Thread.currentThread().getName());
		
		externalServ.callService("");
		
		System.out.println(" After Calling external service "+Thread.currentThread().getName());
		
		return new ResponseEntity<String>("Async Response",HttpStatus.OK);
	}
	
	@GetMapping("/v1/userdetails")
	public ResponseEntity<List<User>> findUser() 
	{
		logger.info(" Before Calling external service "+Thread.currentThread().getName());
		
	    // Kick of multiple, asynchronous lookups
	    CompletableFuture<User> page1;
	    CompletableFuture<User> page2;
	    CompletableFuture<User> page3;
	    List<User> userList = new ArrayList<>();
		try {
			page1 = lookUpServ.findUser("PivotalSoftware");
		    page2 = lookUpServ.findUser("CloudFoundry");
		    page3 = lookUpServ.findUser("Spring-Projects");
		    
		    // Wait until they are all done
		    CompletableFuture.allOf(page1, page2, page3).join();
		    
		    logger.info("--> " + page1.get());
		    logger.info("--> " + page2.get());
		    logger.info("--> " + page3.get());
		    
		    
		    userList.add(page1.get());
		    userList.add(page2.get());
		    userList.add(page3.get());
		    
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}
		
		logger.info(" After Calling external service "+Thread.currentThread().getName());
		
		return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
	}
	 

}
