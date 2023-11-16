package com.fis.hotel.activity.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.ihotelframework.basedao.HisUserActivityLogBaseDao;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.dto.HisDiffsChangeDataDto;
import com.fis.ihotelframework.dto.HisDiffsChangeDataDtoDB;
import com.fis.ihotelframework.dto.SaApplicationConfigDto;
import com.fis.ihotelframework.mapper.HisUserActivityLogDB;
import com.fis.ihotelframework.model.HisUserActivityLog;

@Repository
public class HisUserActivityLogDao extends HisUserActivityLogBaseDao {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public HisUserActivityLog getHisUserActivityLogBykeywordDao(String schema, String propertyCode, String groupCode,
			String keyWord1) {
		final String SQL = "select * from " + schema + Parameter.TBL_HIS_USER_ACTIVITY_LOG
				+ " where PROPERTY_CODE = :P_PROPERTY_CODE AND GROUP_CODE = :P_GROUP_CODE AND KEYWORD1 = :P_KEYWORD1 ORDER BY ID DESC LIMIT 1";
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Parameter.PARAM_PROPERTY_CODE, propertyCode).addValue(Parameter.PARAM_GROUP_CODE, groupCode)
				.addValue(Parameter.PARAM_KEYWORD1, keyWord1);
		return namedParameterJdbcTemplate.queryForObject(SQL, namedParameters,
				new HisUserActivityLogDB.HisUserActivityLogRowMapper());
	}
	
	public List<HisUserActivityLog> getSearchViewChangeDao(String schema, String propertyCode, String typeCode,
			String user, Date fromDate, Date toDate, Long id) {
		final String SQL = "SELECT * FROM " + schema + Parameter.TBL_HIS_USER_ACTIVITY_LOG +" hual WHERE hual.PROPERTY_CODE =:P_PROPERTY_CODE \r\n" + 
				"AND hual.KEYWORD1 = :P_ID AND (hual.TYPE_CODE = :P_1 OR :P_1 IS NULL) AND (hual.USER = :P_USER_CODE OR :P_USER_CODE IS NULL)\r\n" + 
				"AND CAST(hual.DATE AS DATE) BETWEEN CAST(:P_BEGIN_DATE AS DATE) AND CAST(:P_END_DATE AS DATE)";
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Parameter.PARAM_PROPERTY_CODE, propertyCode)
				.addValue(Parameter.PARAM_1, typeCode)
				.addValue(Parameter.PARAM_USER_CODE, user)
				.addValue(Parameter.PARAM_BEGIN_DATE, fromDate)
				.addValue(Parameter.PARAM_END_DATE, toDate)
				.addValue(Parameter.PARAM_ID, id);
		return namedParameterJdbcTemplate.query(SQL, namedParameters,
				new HisUserActivityLogDB.HisUserActivityLogRowMapper());
	}
	
	public List<HisDiffsChangeDataDto> getSearchViewChangeTestDao(String schema, String propertyCode, String typeCode,
			String user, Date fromDate, Date toDate, Long id) throws JsonMappingException, JsonProcessingException {
		final String SQL = "SELECT DIFFS_DESCRIPTION FROM " + schema + Parameter.TBL_HIS_USER_ACTIVITY_LOG +" hual WHERE hual.PROPERTY_CODE =:P_PROPERTY_CODE \r\n" + 
				"AND hual.KEYWORD1 = :P_ID AND (hual.TYPE_CODE = :P_1 OR :P_1 IS NULL) AND (hual.USER = :P_USER_CODE OR :P_USER_CODE IS NULL)\r\n" + 
				"AND CAST(hual.DATE AS DATE) BETWEEN CAST(:P_BEGIN_DATE AS DATE) AND CAST(:P_END_DATE AS DATE)";
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Parameter.PARAM_PROPERTY_CODE, propertyCode)
				.addValue(Parameter.PARAM_1, typeCode)
				.addValue(Parameter.PARAM_USER_CODE, user)
				.addValue(Parameter.PARAM_BEGIN_DATE, fromDate)
				.addValue(Parameter.PARAM_END_DATE, toDate)
				.addValue(Parameter.PARAM_ID, id);
		List<String> res = namedParameterJdbcTemplate.queryForList(SQL, namedParameters,
				String.class);
		List<HisDiffsChangeDataDto> res_model = new ArrayList<HisDiffsChangeDataDto>();
		for (String json : res) {
			if(json != null) {
				ObjectMapper mapper = new ObjectMapper();
				res_model.addAll(mapper.readValue(json, new TypeReference<List<HisDiffsChangeDataDto>>() {}));
			}			
		}		
		return res_model;
	}
}
