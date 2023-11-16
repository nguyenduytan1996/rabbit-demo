package com.fis.hotel.admin.dao;

import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.fis.ihotelframework.basedao.SysMessageBaseDao;
import com.fis.ihotelframework.common.Common;
import com.fis.ihotelframework.common.Parameter;
import com.fis.ihotelframework.model.SysMessage;

@Repository
public class SysMessageDao extends SysMessageBaseDao {
	private static Logger logger = LoggerFactory.getLogger(SysMessageDao.class);

	@Resource(name = "redisTemplate")
	HashOperations<String, String, String> hashOperations;

	public String findMessage(String code, String language) {
		try {
			String schema = "ihotel_sys";
			String result = "";
			String mkey = String.format(Parameter.DC_MSG_ALL_KEY, language);

			if (!hashOperations.hasKey(mkey, code)) {
				Optional<SysMessage> sysMessage = findAllColByCodeAndLanguage(schema, code, language);
				if (sysMessage.isPresent()) {
					SysMessage mess = sysMessage.get();
					String messCode = mess.getMESSAGECODE();
					String messContent = mess.getMESSAGECONTENT();
					hashOperations.put(mkey, messCode, messContent);
					result = messContent;
				}
			}

			result = hashOperations.get(mkey, code);
			if (Common.isNullOrEmpty(result))
				result = "...";

			return result;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
