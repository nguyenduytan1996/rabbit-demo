package com.fis.hotel.activity.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.hotel.activity.service.HisActivityMailLogService;
import com.fis.hotel.activity.service.RestService;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisActivityMailLog;

import fis.core.jdbc.exception.NullObjectException;


@RestController
@RequestMapping(value="HisActivityMailLog")
public class HisActivityMailLogController {
	private static Logger logger = LoggerFactory.getLogger(HisActivityMailLogController.class);
	@Autowired
	RestService sysMessageService;
	@Autowired
	HisActivityMailLogService hisActivityMailLogService;
	
	@PostMapping("/create")
	public MessageDetail create(@RequestParam("schema") String schema, @RequestParam("language") String language,
			@RequestBody HisActivityMailLog HisActivityMailLog) {
		try {
			HisActivityMailLog.setPersisted(false);
			return hisActivityMailLogService.create(schema, language, HisActivityMailLog);
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(sysMessageService.getMessage(MessageCommon.SE000021, language), e));
		}
	}

	@PutMapping("/update")
	public MessageDetail update(@RequestParam("schema") String schema, @RequestParam("language") String language,
			@RequestBody HisActivityMailLog HisActivityMailLog) {
		try {
			HisActivityMailLog.setPersisted(true);
			return hisActivityMailLogService.update(schema, language, HisActivityMailLog);
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(sysMessageService.getMessage(MessageCommon.SE000021, language), e));
		}
	}

	@DeleteMapping("/delete")
	public MessageDetail removeRoom(@RequestParam("schema") String schema, @RequestParam("language") String language,
			@RequestParam("id") Long id) {
		try {
			return hisActivityMailLogService.remove(schema, language, id);
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(sysMessageService.getMessage(MessageCommon.SE000021, language), e));
		}
	}

	@GetMapping("/getByPropertyCode")
	public List<HisActivityMailLog> getOneRoomByPropertyCode(@RequestParam("schema") String schema,
			@RequestParam("propertycode") String propertycode) throws NullObjectException {
		return hisActivityMailLogService.findOneRoomByPropertyCode(schema, propertycode);
	}

	@GetMapping("/findByPropertyCodeAndActiveList")
	public List<HisActivityMailLog> findByPropertyCodeAndActiveList(@RequestParam("schema") String schema,
			@RequestParam("propertycode") String propertycode, @RequestParam("active") boolean active,
			@RequestParam("id") Long id) throws NullObjectException {
		return hisActivityMailLogService.findByPropertyCodeAndActiveList(schema, propertycode, active, id);
	}
	
	@GetMapping("/getAll")
	public List<HisActivityMailLog> findAllData(@RequestParam("schema") String schema
			,@RequestParam("resvid") Long resvid
			,@RequestParam("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(value = "fromemail", required = false) String fromemail, 
			@RequestParam(value = "toemail", required = false) String toemail,
			@RequestParam(value = "useraction", required = false) Long useraction
			) throws NullObjectException {
		return hisActivityMailLogService.findAllData(schema,resvid,beginDate,endDate,fromemail,toemail,useraction);
	}
	
}
