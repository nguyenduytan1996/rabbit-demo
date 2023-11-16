package com.fis.hotel.activity.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fis.ihotelframework.dto.HisDiffsChangeDataDto;
import com.fis.ihotelframework.model.HisUserActivityLog;

public interface HisUserActivityLogService {

	boolean createOrUpdate(String schema, String propertyCode, HisUserActivityLog model);

	Optional<HisUserActivityLog> findOne(String schema, Long id);

	void removeById(String schema, Long id, String language);

	List<HisUserActivityLog> getSearchViewChange(String schema, String propertyCode, String typeCode, String user,
			Date fromDate, Date toDate, Long id);

	List<HisDiffsChangeDataDto> getSearchViewChangeTest(String schema, String propertyCode, String typeCode,
			String user, Date fromDate, Date toDate, Long id) throws JsonMappingException, JsonProcessingException;

}
