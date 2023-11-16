package com.fis.hotel.activity.service;

import java.util.Date;
import java.util.List;

import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisActivityMailLog;

import fis.core.jdbc.exception.NullObjectException;

public interface HisActivityMailLogService {

	public MessageDetail create(String schema, String language, HisActivityMailLog data);

	public MessageDetail update(String schema, String language, HisActivityMailLog data);

	public MessageDetail remove(String schema, String language, Long id);

	public List<HisActivityMailLog> findOneRoomByPropertyCode(String schema, String propertycode) throws NullObjectException;

	public List<HisActivityMailLog> findByPropertyCodeAndActiveList(String schema, String propertycode, boolean active, Long id) throws NullObjectException;
	
	public List<HisActivityMailLog> findAllData(String schema, Long resvid, Date beginDate, Date endDate,
			String fromemail, String toemail, Long useraction) throws NullObjectException;

}
