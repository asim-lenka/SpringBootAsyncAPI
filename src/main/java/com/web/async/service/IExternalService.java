/**
 * 
 */
package com.web.async.service;

import java.util.concurrent.CompletableFuture;

import com.web.async.model.User;

/**
 * @author asiml
 *
 */
public interface IExternalService {
	
	public void callService(String url);

}
