package com.fis.hotel.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fis.hotel.admin.service.SysMessageService;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.SysMessage;

import fis.core.jdbc.exception.NullObjectException;

@RestController
@RequestMapping(value = "message")
public class SysMessageController {
	@Autowired
	SysMessageService sysMessageService;

	@PostMapping("/create")
	public MessageDetail create(@RequestBody SysMessage model, @RequestParam("language") String language) {
		try {
			model.setPersisted(false);
			return sysMessageService.create(model, language);
		} catch (Exception e) {
			return new MessageDetail(3, String.format(MessageCommon.getMessage(MessageCommon.SE000021, language), e));
		}
	}

	@PutMapping("/update")
	public MessageDetail update(@RequestBody SysMessage model, @RequestParam("language") String language) {
		try {
			model.setPersisted(true);
			return sysMessageService.update(model, language);
		} catch (Exception e) {
			return new MessageDetail(3, String.format(MessageCommon.getMessage(MessageCommon.SE000021, language), e));
		}
	}

	@GetMapping("/getOne")
	public Optional<SysMessage> getOne(@RequestParam("id") Long id) throws NullObjectException {
		return sysMessageService.getOne(id);
	}

	@GetMapping("/getAll")
	public List<SysMessage> getAll() {
		return sysMessageService.getAll();
	}

	@DeleteMapping("/delete")
	public MessageDetail remove(@RequestParam("id") Long id, @RequestParam("language") String language) {
		try {
			return sysMessageService.remove(id, language);
		} catch (Exception e) {
			return new MessageDetail(3, String.format(MessageCommon.getMessage(MessageCommon.SE000021, language), e));
		}
	}

//	@DeleteMapping("/delete/all")
//	public MessageDetail removeAll(@RequestParam("language") String language) {
//		try {
//			return sysMessageService.removeAll(language);
//		} catch (Exception e) {
//			return new MessageDetail(3, String.format(MessageCommon.getMessage(MessageCommon.SE000021, language), e));
//		}
//	}

}
