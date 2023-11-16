package com.fis.hotel.activity.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.hotel.activity.service.HisUserActivityTypeService;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisUserActivityType;

import fis.core.jdbc.exception.NullObjectException;

@RestController
@RequestMapping(value = "HisUserActivityType")
public class HisUserActivityTypeController {
	private static Logger logger = LoggerFactory.getLogger(HisUserActivityTypeController.class);

	@Autowired
	HisUserActivityTypeService hisUserActivityTypeService;

	@PostMapping("/create")
	public MessageDetail create(@RequestBody HisUserActivityType model, @RequestParam("language") String language,
			@RequestParam("schema") String schema) {
		try {
			model.setPersisted(false);
			return hisUserActivityTypeService.createOrUpdate(schema, language, model);
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(hisUserActivityTypeService.getMessageExtends(MessageCommon.SE000021, language), e));
		}
	}

	@PutMapping("/update")
	public MessageDetail update(@RequestBody HisUserActivityType model, @RequestParam("language") String language,
			@RequestParam("schema") String schema) {
		try {
			model.setPersisted(true);
			return hisUserActivityTypeService.createOrUpdate(schema, language, model);
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(hisUserActivityTypeService.getMessageExtends(MessageCommon.SE000021, language), e));
		}
	}

	@GetMapping("/getOne")
	public Optional<HisUserActivityType> getOne(@RequestParam("id") Long id, @RequestParam("schema") String schema)
			throws NullObjectException {
		return hisUserActivityTypeService.findOne(schema, id);
	}

	@DeleteMapping("/delete")
	public MessageDetail remove(@RequestParam("id") Long id, @RequestParam("language") String language,
			@RequestParam("schema") String schema) {
		try {
			return hisUserActivityTypeService.removeById(schema, id, language);
		} catch (Exception e) {
			return new MessageDetail(3,
					String.format(hisUserActivityTypeService.getMessageExtends(MessageCommon.SE000021, language), e));
		}
	}
	
	@GetMapping("/findsByActive")
	public List<HisUserActivityType> findsByActive(@RequestParam("schema") String schema)
			throws NullObjectException {
		return hisUserActivityTypeService.findsByActive(schema);
	}
}
