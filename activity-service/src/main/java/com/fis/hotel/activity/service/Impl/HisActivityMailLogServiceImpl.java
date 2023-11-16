package com.fis.hotel.activity.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fis.hotel.activity.dao.HisActivityMailLogDao;
import com.fis.hotel.activity.service.HisActivityMailLogService;
import com.fis.hotel.activity.service.RestService;
import com.fis.ihotelframework.common.MessageCommon;
import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisActivityMailLog;

import fis.core.jdbc.exception.NullObjectException;


@Service
public class HisActivityMailLogServiceImpl extends HisActivityMailLogDao implements HisActivityMailLogService {
	
	@Autowired
	RestService sysMessageService;
	
	@Override
	@Transactional
	public MessageDetail create(String schema, String language, HisActivityMailLog data) {
		if (save(schema, data).getId() != null) {
			return new MessageDetail(1, sysMessageService.getMessage(MessageCommon.SI000021, language));
		} else {
			return new MessageDetail(2, sysMessageService.getMessage(MessageCommon.SW000021, language));
		}
	}

	@Override
	@Transactional
	public MessageDetail update(String schema, String language, HisActivityMailLog data) {
		if (save(schema, data).getId() != null) {
			return new MessageDetail(1, sysMessageService.getMessage(MessageCommon.SI000021, language));
		} else {
			return new MessageDetail(2, sysMessageService.getMessage(MessageCommon.SW000021, language));
		}
	}

	@Override
	@Transactional
	public MessageDetail remove(String schema, String language, Long id) {
		deleteById(schema, id);
		return new MessageDetail(1, sysMessageService.getMessage(MessageCommon.SI000022, language));
	}

	@Override
	@Transactional(readOnly = true)
	public List<HisActivityMailLog> findOneRoomByPropertyCode(String schema, String propertycode) throws NullObjectException {
		try {
			return findByPropertyCode(schema, propertycode);
		} catch (EmptyResultDataAccessException e) {
			throw new NullObjectException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<HisActivityMailLog> findByPropertyCodeAndActiveList(String schema, String propertycode, boolean active,
			Long id) throws NullObjectException {
		try {
			return findByPropertyCodeAndActive(schema, propertycode, active).stream().filter(i -> i.getId() != id)
					.collect(Collectors.toList());
		} catch (EmptyResultDataAccessException e) {
			throw new NullObjectException();
		}

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HisActivityMailLog> findAllData(String schema,Long resvid, Date beginDate, Date endDate,
			String fromemail, String toemail, Long useraction) throws NullObjectException {
		try {
//			return findAll(schema).stream().filter(i -> i.getUSER01() != Math.toIntExact(resvid)).collect(Collectors.toList());
			return getBykeywordDao(schema,resvid,beginDate,endDate,fromemail,toemail,useraction);
		} catch (EmptyResultDataAccessException e) {
			throw new NullObjectException();
		}
	}
}
