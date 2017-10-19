package com.api.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.api.service.TmppSyncFashionpos0aService;

@Component
public class TestTask
{
	@Autowired
	private TmppSyncFashionpos0aService service;
	
	@Scheduled(fixedDelay = 3000)
	public void query() 
	{
		try
		{
			int c = service.getCount(null);
			System.out.println("query ==> "+c);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
