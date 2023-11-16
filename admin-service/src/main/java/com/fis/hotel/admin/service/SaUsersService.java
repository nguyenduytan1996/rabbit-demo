package com.fis.hotel.admin.service;

import com.fis.ihotelframework.model.SaUsers;

public interface SaUsersService {
	/**/
	SaUsers checkLogin(String fullUserLogin);	

	SaUsers oAuth(String grant, String userName);
	
}
