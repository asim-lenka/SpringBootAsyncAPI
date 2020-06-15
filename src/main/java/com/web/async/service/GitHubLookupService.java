/**
 * 
 */
package com.web.async.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.web.async.model.User;

/**
 * @author asiml
 *
 */
@Service
public class GitHubLookupService {
	
	  private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

	  @Autowired
	  private RestTemplate restTemplate;

	  @Async
	  public CompletableFuture<User> findUser(String user) throws InterruptedException {
		logger.info("Start Calling GIT service "+Thread.currentThread().getName());  
	    logger.info("Looking up " + user);
	    
	    String url = String.format("https://api.github.com/users/%s", user);
	    User results = restTemplate.getForObject(url, User.class);
	    // Artificial delay of 1s for demonstration purposes
	    Thread.sleep(2000L);
	    
	    logger.info("End Calling GIT service "+Thread.currentThread().getName());
	    
	    return CompletableFuture.completedFuture(results);
	  }

}
