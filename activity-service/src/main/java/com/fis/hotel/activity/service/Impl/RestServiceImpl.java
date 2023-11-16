package com.fis.hotel.activity.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fis.hotel.activity.service.RestService;

@Service
public class RestServiceImpl implements RestService {
	final static Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);
	
	@Autowired
	RestTemplate restTemplate;
	@Value("${server.system.url}")
	private String systemUrl;
	
	@Override
	@Transactional(readOnly = true)
	public String getMessage(String code, String language) {
//		return findMessage(code, language);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		params.put("language", language);
		logger.info("=====> Check message success");
		return restTemplate.getForObject(systemUrl + "/SysMessage/getByCodeAndLanguage/{code}/{language}", String.class,
				params);
	}

}
