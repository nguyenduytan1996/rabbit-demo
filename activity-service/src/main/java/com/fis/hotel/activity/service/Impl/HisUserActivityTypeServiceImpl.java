package com.fis.hotel.activity.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fis.hotel.activity.dao.HisUserActivityTypeDao;
import com.fis.hotel.activity.service.HisUserActivityTypeService;
import com.fis.hotel.activity.service.RestService;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisUserActivityType;

@Service
public class HisUserActivityTypeServiceImpl extends HisUserActivityTypeDao implements HisUserActivityTypeService {

	@Autowired
	RestService sysMessageService;

	@Override
	@Transactional
	public MessageDetail createOrUpdate(String schema, String language, HisUserActivityType model) {
		if (save(schema, model).getId() != null) {
			return new MessageDetail(1, sysMessageService.getMessage(MessageCommon.SI000021, language));
		} else {
			return new MessageDetail(2, sysMessageService.getMessage(MessageCommon.SW000021, language));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<HisUserActivityType> findOne(String schema, Long id) {
		return findById(schema, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HisUserActivityType> findsByActive(String schema) {		
		return findAllByActive(schema, true);		
	}
	
	@Override
	@Transactional
	public MessageDetail removeById(String schema, Long id, String language) {
		deleteById(schema, id);
		return new MessageDetail(1, sysMessageService.getMessage(MessageCommon.SI000022, language));
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getMessageExtends(String message, String language) {
		return sysMessageService.getMessage(message, language);	
	}
}
