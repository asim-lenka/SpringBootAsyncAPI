package com.web.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ExternalServiceImpl implements IExternalService{
	
	private static final Logger logger = LoggerFactory.getLogger(ExternalServiceImpl.class);

	@Override
	@Async
	public void callService(String url) {
		// TODO Auto-generated method stub
		logger.info("Start Calling external service "+Thread.currentThread().getName());
		
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("End Calling external service "+Thread.currentThread().getName());
	}

}
