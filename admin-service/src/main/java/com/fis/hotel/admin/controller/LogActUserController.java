package com.fis.hotel.admin.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.hotel.admin.service.LogActUserService;
import com.fis.ihotelframework.model.LogActUser;

import fis.core.jdbc.exception.MySqlException;

@RestController
@RequestMapping(value = "logactuser")
public class LogActUserController {
	
	@Autowired
	private LogActUserService logActUserService;

	@GetMapping("/getDataByUserAndSchema")
	public List<Map<String, Object>> getDataByUserAndSchema(
			@RequestParam("useract") String userAct,
			@RequestParam("schemaact") String schemaAct,
			@RequestParam(name = "dateact", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateAct) {
		
		try {
			return logActUserService.getDataByUserAndSchema(userAct, schemaAct, dateAct);
		} catch(MySqlException e) {
			System.out.println(e.getMessage());
			return Collections.emptyList();
		}
	}
	
	@PostMapping("/writeLogActUser")
	public void writeLogActUser(@RequestBody Map<String, Object> body) throws Exception 
	{
		LogActUser model = new LogActUser();
		model.setId(null);
		model.setPersisted(false);
		model.setUSERACT(body.get("useract").toString());
		model.setSCHEMAACT(body.get("schemaact").toString());
		model.setACTIONCODE(body.get("action").toString());
		model.setFUNCTIONACT(body.get("function").toString());
		model.setNOTE(body.get("note").toString());		
		model.setOTHER1(body.get("ip").toString());	
		model.setOTHER2(body.get("host").toString());	
		model.setPROPERTYCODE(body.get("propertycode").toString());
		logActUserService.createOrUpdate(model);
	}
	
	@PostMapping("/getDataFull")
	public List<Map<String, Object>> getDataFull(@RequestBody Map<String, Object> body) {	
		return logActUserService.getDataFull(body.get("begin").toString(), body.get("end").toString());
	}
	
	@GetMapping("/getDataByDate")
	public List<Map<String, Object>> getDataByDate() {		
		return logActUserService.getDataByDate();
	}
}
