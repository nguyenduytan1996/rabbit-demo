package com.fis.hotel.admin.service;

import java.util.List;
import java.util.Optional;

import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.SysMessage;

public interface SysMessageService {
	MessageDetail create(SysMessage model, String language);

	MessageDetail update(SysMessage model, String language);

	Optional<SysMessage> getOne(Long id);

	List<SysMessage> getAll();

	MessageDetail remove(Long id, String language);

	MessageDetail removeAll(String language);

	String getMessage(String code, String language);
}
