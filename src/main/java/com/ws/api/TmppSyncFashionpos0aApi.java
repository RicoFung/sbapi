package com.ws.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.common.Factory;
import com.ws.entity.TmppSyncFashionpos0a;
import com.ws.service.TmppSyncFashionpos0aService;

@WebService(endpointInterface="com.ws.api.ITmppSyncFashionpos0aApi", serviceName="TmppSyncFashionpos0aApi")
public class TmppSyncFashionpos0aApi extends SpringBeanAutowiringSupport implements ITmppSyncFashionpos0aApi
{
	static Logger log = LoggerFactory.getLogger("sbApi");

//	@Autowired
//	private TmppSyncFashionpos0aService service;
	private TmppSyncFashionpos0aService service = Factory.getTmppSyncFashionpos0aService();

	@Override
	public Map<String, String> add(TmppSyncFashionpos0a po)
	{
		Map<String, String> result = new HashMap<String, String>();
		try
		{
			service.add(po);
			result.put("success", ""+true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.put("success", ""+false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, String> addBatch(List<TmppSyncFashionpos0a> poList)
	{
		Map<String, String> result = new HashMap<String, String>();
		try
		{
			service.addBatch(poList);
			result.put("success", ""+true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result.put("success", ""+false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, String> del(Map<String, String> param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> upd(TmppSyncFashionpos0a param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> query(String param1, String param2)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
