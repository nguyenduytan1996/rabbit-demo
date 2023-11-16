package com.fis.hotel.activity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import com.fis.ihotelframework.basedao.HisActivityMailLogBaseDao;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.dto.HkpAttendantSheetsDtoDB;
import com.fis.ihotelframework.mapper.HisActivityMailLogDB;
import com.fis.ihotelframework.model.HisActivityMailLog;



@Repository
public class HisActivityMailLogDao extends HisActivityMailLogBaseDao {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<HisActivityMailLog> getBykeywordDao(String schema,Long resvid, Date beginDate, Date endDate,
			String fromemail, String toemail, Long useraction) {
		final String SQL = "select ac.ID, ac.USER_ACTION,ac.FROM_EMAIL, ac.TO_EMAIL, ac.CONTENT_MAIL, ac.SENT_TIME,ac.USER_01,ac.IS_SENT,"
				+ " ac.EXCEPTION, us.DISPLAY_NAME AS USER_04, ac.USER_02, ac.USER_03, ac.USER_05, ac.USER_06, ac.USER_07,"
				+ " ac.USER_08, ac.USER_09, ac.USER_10, ac.USER_11, ac.USER_12, ac.CREATE_USER, ac.CREATE_DATE,ac.UPDATED_USER,ac.UPDATED_DATE from " + schema + Parameter.TBL_HIS_ACTIVITY_MAIL_LOG
				+ " ac INNER JOIN " + schema + Parameter.TBL_SYS_USERS +" us on ac.USER_ACTION = us.ID \r\n"
				+ " where ac.USER_01 = :P_RESV_ID \r\n"
				+ "AND (CAST(ac.SENT_TIME AS DATE) BETWEEN CAST(:P_BEGIN_DATE AS DATE) AND CAST(:P_END_DATE AS DATE) OR :P_BEGIN_DATE  IS NULL OR :P_END_DATE IS NULL)\r\n"
				+ "	AND ( ac.FROM_EMAIL = :P_FROM_EMAIL OR :P_FROM_EMAIL IS NULL OR :P_FROM_EMAIL ='') \r\n"
				+ "	AND ( ac.TO_EMAIL = :P_TO_EMAIL OR :P_TO_EMAIL IS NULL OR :P_TO_EMAIL ='') \r\n"
				+ "	AND ( ac.USER_ACTION = :P_USER_ACTION OR :P_USER_ACTION IS NULL ) \r\n"
				+ " ORDER BY ac.ID DESC";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue(Parameter.PARAM_RESV_ID, resvid)
				.addValue(Parameter.PARAM_BEGIN_DATE, beginDate)
				.addValue(Parameter.PARAM_END_DATE, endDate)
				.addValue(Parameter.PARAM_FROM_EMAIL, fromemail)
				.addValue(Parameter.PARAM_TO_EMAIL, toemail)
				.addValue(Parameter.PARAM_USER_ACTION, useraction)
				;
		return namedParameterJdbcTemplate.query(SQL, namedParameters,
				new HisActivityMailLogDB.HisActivityMailLogRowMapper());
	}
}
