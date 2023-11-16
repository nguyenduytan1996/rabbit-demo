package com.fis.hotel.admin.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fis.ihotelframework.basedao.LogActUserBaseDao;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.mapper.LogActUserDB;
import com.fis.ihotelframework.mapper.LogActUserMapper;
import com.fis.ihotelframework.model.LogActUser;

import fis.core.jdbc.exception.MySqlException;

@Repository
public class LogActUserDao extends LogActUserBaseDao {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Map<String, Object>> getDataByUserAndSchemaDao(String userAct, String schemaAct, Date dateAct)
			throws MySqlException {
		String QUERY = "SELECT * \r\n" + "FROM ihotel_log" + Parameter.TBL_LOG_ACT_USER + "\r\n"
				+ "WHERE USER_ACT = :P_USER_ACT\r\n" + "AND SCHEMA_ACT = :P_SCHEMA_ACT\r\n"
				+ "AND (CAST(DATE_ACT as DATE) = CAST(:P_DATE_ACT AS DATE) OR :P_DATE_ACT IS NULL)";

		try {
			SqlParameterSource namedParams = new MapSqlParameterSource().addValue(Parameter.PARAM_SCHEMA_ACT, schemaAct)
					.addValue(Parameter.PARAM_USER_ACT, userAct).addValue(Parameter.PARAM_DATE_ACT, dateAct);

			return namedParameterJdbcTemplate.query(QUERY, namedParams, new LogActUserMapper());
		} catch (DataAccessException e) {
			throw new MySqlException(e.getMessage());
		}
	}

	public List<Map<String, Object>> getDataByDateDao() {
		String QUERY = "SELECT * \r\n" + "FROM ihotel_log" + Parameter.TBL_LOG_ACT_USER + " WHERE SUBSTR(DATE_ACT, 1, 11) = DATE_FORMAT(NOW(),'%d-%m-%Y')";

		try {
			SqlParameterSource namedParams = new MapSqlParameterSource();
			return namedParameterJdbcTemplate.queryForList(QUERY, namedParams);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	public List<Map<String, Object>> getDataFullDao(String begin, String end) {
		String QUERY = "SELECT * \r\n" + "FROM ihotel_log" + Parameter.TBL_LOG_ACT_USER + " WHERE STR_TO_DATE(DATE_ACT,'%d-%m-%Y') BETWEEN  CAST(:P_BEGIN_DATE AS DATE) AND CAST(:P_END_DATE AS DATE)";

		try {
			SqlParameterSource namedParams = new MapSqlParameterSource()
					.addValue(Parameter.PARAM_BEGIN_DATE, begin)
					.addValue(Parameter.PARAM_END_DATE, end);
			return namedParameterJdbcTemplate.queryForList(QUERY, namedParams);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}