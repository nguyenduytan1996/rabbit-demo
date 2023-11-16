package com.fis.hotel.admin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fis.hotel.admin.dao.LogActUserDao;
import com.fis.hotel.admin.dao.SaUsersDao;
import com.fis.hotel.admin.service.SaUsersService;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.model.LogActUser;
import com.fis.ihotelframework.model.SaUsers;

@Service
public class SaUsersServiceImpl extends SaUsersDao implements SaUsersService{
	private static Logger logger = LoggerFactory.getLogger(SaUsersServiceImpl.class);
	
	@Autowired
	LogActUserDao logActUserDao;

	/**/
	@Override
	@Transactional(readOnly = true)
	public SaUsers checkLogin(String fullUserLogin) {
		return checkLoginDao(fullUserLogin);
	}
	
	@Override	
	@Transactional
	public SaUsers oAuth(String grant, String userName) {
		try {		
			SaUsers user = oAuthDao(Parameter.SCHEMA_SYS, userName, grant);		
			LogActUser log = new LogActUser();
			log.setId(null);
			log.setPersisted(false);
			log.setUSERACT(userName);
			log.setSCHEMAACT(Parameter.SCHEMA_SYS);
			log.setACTIONCODE("Login");
			log.setFUNCTIONACT("call from external API");
			log.setNOTE("login call from external API");
			logActUserDao.save(Parameter.SCHEMA_LOG, log);
			return user;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
}
