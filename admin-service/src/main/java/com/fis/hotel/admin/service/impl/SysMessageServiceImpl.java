package com.fis.hotel.admin.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fis.hotel.admin.dao.SysMessageDao;
import com.fis.hotel.admin.service.SysMessageService;
import com.fis.ihotelframework.common.Common;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.SysMessage;

@Service
public class SysMessageServiceImpl extends SysMessageDao implements SysMessageService {
	private static Logger logger = LoggerFactory.getLogger(SysMessageServiceImpl.class);

	@Autowired
	AdmSbRedisCacheManagerImpl redisCacheManager;

	@Override
	@Transactional
	public MessageDetail create(SysMessage model, String language) {
		Long id = save(model).getId();

		String code = model.getMESSAGECODE();
		String lang = model.getLANGUAGE();
		redisCacheManager.DeleteHashKey(String.format(Parameter.DC_MSG_ALL_KEY, lang), code);

		if (id != null) {
			return new MessageDetail(1, MessageCommon.getMessage(MessageCommon.SI000021, language));
		} else {
			return new MessageDetail(2, MessageCommon.getMessage(MessageCommon.SW000021, language));
		}
	}

	@Override
	@Transactional
	public MessageDetail update(SysMessage model, String language) {
		Long id = save(model).getId();

		String code = model.getMESSAGECODE();
		String lang = model.getLANGUAGE();
		redisCacheManager.DeleteHashKey(String.format(Parameter.DC_MSG_ALL_KEY, lang), code);

		if (id != null) {
			return new MessageDetail(1, MessageCommon.getMessage(MessageCommon.SI000021, language));
		} else {
			return new MessageDetail(2, MessageCommon.getMessage(MessageCommon.SW000021, language));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SysMessage> getOne(Long id) {
		return findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysMessage> getAll() {
		return findAll();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageDetail remove(Long id, String language) {
		Optional<SysMessage> msg = findById(id);
		String code = "";
		String lang = "";
		if (msg.isPresent()) {
			SysMessage m = msg.get();
			code = m.getMESSAGECODE();
			lang = m.getLANGUAGE();
		}

		deleteById(id);

		if (!Common.isNullOrEmpty(code) && !Common.isNullOrEmpty(lang)) {
			redisCacheManager.DeleteHashKey(String.format(Parameter.DC_MSG_ALL_KEY, lang), code);
		}

		return new MessageDetail(1, MessageCommon.getMessage(MessageCommon.SI000022, language));
	}

	@Override
	@Transactional
	public MessageDetail removeAll(String language) {
		deleteAll();
		redisCacheManager.RemoveByPrefix(String.format(Parameter.DC_MSG_ALL_KEY, "*"));
		return new MessageDetail(1, MessageCommon.getMessage(MessageCommon.SI000022, language));
	}

	@Override
	@Transactional(readOnly = true)
	public String getMessage(String code, String language) {
		return findMessage(code, language);
	}

}
