package com.fis.hotel.activity.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.hotel.activity.service.HisUserActivityLogService;
import com.fis.hotel.activity.service.RestService;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.dto.HisDiffsChangeDataDto;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisUserActivityLog;

import fis.core.jdbc.exception.NullObjectException;

@RestController
@RequestMapping(value = "HisUserActivityLog")
public class HisUserActivityLogController {
	private static Logger logger = LoggerFactory.getLogger(HisUserActivityLogController.class);

	@Autowired
	HisUserActivityLogService hisUserActivityLogService;
	
	@Autowired
	RestService restService;

	@PostMapping("/create")
	@Async
	public boolean create(@RequestParam("schema") String schema, @RequestParam("propertyCode") String propertyCode,
			@RequestBody HisUserActivityLog model) {
		try {
			model.setPersisted(false);
			return hisUserActivityLogService.createOrUpdate(schema, propertyCode, model);
		} catch (Exception e) {
			return false;
		}
	}

	@PutMapping("/update")
	public boolean update(@RequestParam("schema") String schema, @RequestParam("propertyCode") String propertyCode,
			@RequestBody HisUserActivityLog model) {
		try {
			model.setPersisted(true);
			return hisUserActivityLogService.createOrUpdate(schema, propertyCode, model);
		} catch (Exception e) {
			return false;
		}
	}

	@GetMapping("/getOne")
	public Optional<HisUserActivityLog> getOne(@RequestParam("id") Long id, @RequestParam("schema") String schema)
			throws NullObjectException {
		return hisUserActivityLogService.findOne(schema, id);
	}

	@DeleteMapping("/delete")
	public MessageDetail remove(@RequestParam("id") Long id, @RequestParam("language") String language,
			@RequestParam("schema") String schema) {
		try {
			hisUserActivityLogService.removeById(schema, id, language);
			return new MessageDetail(1, restService.getMessage(MessageCommon.SI000022, language));
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(restService.getMessage(MessageCommon.SE000021, language), e));
		}
	}

	@GetMapping("/getSearchViewChange")
	public List<HisUserActivityLog> getSearchViewChange(@RequestParam("schema") String schema
			,@RequestParam("propertyCode") String propertyCode
			,@RequestParam("language") String language
			,@RequestParam(value = "typeCode", required = false) String typeCode
			,@RequestParam(value = "user", required = false) String user
			,@RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate
			,@RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
			,@RequestParam("id") Long id) {
		try {		
			return hisUserActivityLogService.getSearchViewChange(schema, propertyCode, typeCode, user, fromDate,
					toDate, id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return Collections.emptyList();
		}
	}
	
	@GetMapping("/getSearchViewChangeTest")
	public List<HisDiffsChangeDataDto> getSearchViewChangeTest(@RequestParam("schema") String schema
			,@RequestParam("propertyCode") String propertyCode
			,@RequestParam("language") String language
			,@RequestParam(value = "typeCode", required = false) String typeCode
			,@RequestParam(value = "user", required = false) String user
			,@RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate
			,@RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
			,@RequestParam("id") Long id) {
		try {		
			return hisUserActivityLogService.getSearchViewChangeTest(schema, propertyCode, typeCode, user, fromDate,
					toDate, id);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return Collections.emptyList();
		}		

	}
}
