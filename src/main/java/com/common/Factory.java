package com.common;

import com.ws.service.TmppSyncFashionpos0aService;


public class Factory {
	public static TmppSyncFashionpos0aService getTmppSyncFashionpos0aService(){return (TmppSyncFashionpos0aService) BeanFactory.getBean("tmppSyncFashionpos0aService");}
}
