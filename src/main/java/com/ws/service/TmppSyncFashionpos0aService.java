package com.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.dao.TmppSyncFashionpos0aDao;
import com.ws.entity.TmppSyncFashionpos0a;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service
public class TmppSyncFashionpos0aService extends BaseService<TmppSyncFashionpos0a,Long>
{
	@Autowired
	private TmppSyncFashionpos0aDao dao;

	@Override
	public BaseDao<TmppSyncFashionpos0a,Long> getEntityDao() 
	{
		return dao;
	}
	
	public void addBatch(List<TmppSyncFashionpos0a> poList) throws Exception
	{
		for(TmppSyncFashionpos0a po : poList)
		{
			dao.add(po);
		}
		throw new Exception("test");
	}
}
