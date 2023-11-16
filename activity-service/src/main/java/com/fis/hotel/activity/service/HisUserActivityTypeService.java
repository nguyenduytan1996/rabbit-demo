package com.fis.hotel.activity.service;

import java.util.List;
import java.util.Optional;

import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisUserActivityType;

public interface HisUserActivityTypeService {

	MessageDetail createOrUpdate(String schema, String language, HisUserActivityType model);

	Optional<HisUserActivityType> findOne(String schema, Long id);

	MessageDetail removeById(String schema, Long id, String language);

	String getMessageExtends(String message, String language);

	List<HisUserActivityType> findsByActive(String schema);
	
	
	
}
