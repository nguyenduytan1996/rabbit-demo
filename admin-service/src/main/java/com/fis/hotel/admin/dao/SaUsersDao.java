package com.fis.hotel.admin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fis.ihotelframework.basedao.SaUsersBaseDao;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.mapper.SaUsersDB;
import com.fis.ihotelframework.mapper.SysUsersDB;
import com.fis.ihotelframework.model.SaUsers;
import com.fis.ihotelframework.model.SysUsers;

import fis.core.jdbc.exception.MySqlException;

@Repository
public class SaUsersDao extends SaUsersBaseDao {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public SaUsers checkLoginDao(String fullUserLogin) {
		String QUERY = null;
		QUERY = "SELECT * FROM " + Parameter.TBL_SA_USERS + " WHERE USER_NAME = :P_USER_NAME";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(Parameter.PARAM_USER_NAME, fullUserLogin.trim());
		SaUsers res = namedParameterJdbcTemplate.queryForObject(QUERY, namedParameters, new SaUsersDB.SaUsersRowMapper());
		return res;
	}
	
	public SaUsers oAuthDao(String schema, String userName, String grant) {
		try {
			String QUERY = null;	
			QUERY = "SELECT * FROM " + schema + Parameter.TBL_SA_USERS
					+ " WHERE LOWER(CLIENT_ID) = LOWER(:P_USER_NAME) AND LOWER(GRANTTYPE) = LOWER(:P_1) AND ACTIVE = 1";
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue(Parameter.PARAM_USER_NAME,
					userName).addValue(Parameter.PARAM_1,grant);
			SaUsers user = namedParameterJdbcTemplate.queryForObject(QUERY, namedParameters,
					SaUsersDB.ROW_MAPPER);
			if(user != null && user.getSCHEMA().equals("ihotel_sys")) {
				return user;
			}else if(user != null && (user.getSCHEMA() != null || user.getSCHEMA() != "")) {
				String _schema = user.getSCHEMA();
				String _propertyCode = user.getPROPERTYCODE();
				String QUERY1 = "SELECT COUNT(H.ID) AS _RESULT FROM " + _schema + Parameter.TBL_DTA_HOTEL + " H  \r\n"
						+ "INNER JOIN (SELECT IS_TRIAL,VERSION, PROPERTY_ID FROM " + _schema + Parameter.TBL_DTA_HOTEL_CONTRACTS + " WHERE NOW() BETWEEN START_CONTRACT AND IF((END_CONTRACT IS NULL OR END_CONTRACT = NULL), NOW(), END_CONTRACT)) dhc ON H.ID = dhc.PROPERTY_ID \r\n"
						+ "WHERE H.PROPERTY_CODE = :P_PROPERTY_CODE AND (H.IS_DEFAULT = 1 OR H.ACTIVE = 1)";
				SqlParameterSource param = new MapSqlParameterSource()
						.addValue(Parameter.PARAM_PROPERTY_CODE, _propertyCode);
				Integer rep = namedParameterJdbcTemplate.queryForObject(QUERY1, param,
						Integer.class);
				return rep > 0 ? user : null;
			}
			return user;			
		} catch (Exception e) {
			return null;
		}
	}
}