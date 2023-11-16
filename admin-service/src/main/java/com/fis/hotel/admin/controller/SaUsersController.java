package com.fis.hotel.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.hotel.admin.service.SaUsersService;
import com.fis.ihotelframework.model.SaUsers;
import com.fis.ihotelframework.model.SysUsers;

import fis.core.jdbc.exception.MySqlException;

@RestController
@RequestMapping(value = "users")
public class SaUsersController {
	@Autowired
	SaUsersService saUsersService;
	
	/**/
	@GetMapping("/checkLogin")
	public SaUsers checkLogin(@RequestParam("username") String fullUserLogin) {
		return saUsersService.checkLogin(fullUserLogin);
	}
	
	@GetMapping("/oAuth")
	public SaUsers oAuth(@RequestParam("userName") String userName, @RequestParam("grant") String grant) {
		return saUsersService.oAuth(grant, userName);
	}
}
