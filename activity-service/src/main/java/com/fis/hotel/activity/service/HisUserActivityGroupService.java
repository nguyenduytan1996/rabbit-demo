package com.fis.hotel.activity.service;

import java.util.Optional;

import com.fis.ihotelframework.dto.MessageDetail;
import com.fis.ihotelframework.model.HisUserActivityGroup;

public interface HisUserActivityGroupService {

	MessageDetail createOrUpdate(String schema, String language, HisUserActivityGroup model);

	Optional<HisUserActivityGroup> findOne(String schema, Long id);

	MessageDetail removeById(String schema, Long id, String language);

	String getMessageExtends(String message, String language);

}
