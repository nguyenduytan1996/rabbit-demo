package com.fis.hotel.admin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fis.ihotelframework.model.LogActUser;

import fis.core.jdbc.exception.MySqlException;

public interface LogActUserService {

	List<Map<String, Object>> getDataByUserAndSchema(String userAct, String schemaAct, Date dateAct) throws MySqlException;

	void createOrUpdate(LogActUser body) throws Exception;

	List<Map<String, Object>> getDataByDate();

	List<Map<String, Object>> getDataFull(String begin, String end);
}
