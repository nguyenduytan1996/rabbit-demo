package com.fis.hotel.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fis.hotel.admin.configuration.RabbitMQProperties;
import com.fis.hotel.admin.dao.LogActUserDao;
import com.fis.hotel.admin.service.LogActUserService;
import com.fis.ihotelframework.model.LogActUser;

import fis.core.jdbc.exception.MySqlException;
import fis.core.jdbc.exception.TransactionException;

@Service
public class LogActUserServiceImpl extends LogActUserDao implements LogActUserService {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
    @Autowired
    RabbitMQProperties rabbitMQProperties;

	@Override
	public void createOrUpdate(LogActUser body) throws Exception {
		try {
			amqpTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.createOrUpdateLogActUser", body);
		}catch(Exception ex) {
			throw new Exception();
		}			
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getDataByUserAndSchema(String userAct, String schemaAct, Date dateAct) throws MySqlException {

		return getDataByUserAndSchemaDao(userAct, schemaAct, dateAct);
	}
	
	@RabbitListener(queues = "${skybeds.rabbitmq.queueName}")
	@Transactional(rollbackFor = TransactionException.class)
	public void createOrUpdateConsumer(LogActUser model) throws TransactionException {
		try {
			if (save("ihotel_log", model).getId() != null) {
				
			} else {
				throw new TransactionException();
			}
		}catch(TransactionException ex) {
			throw new TransactionException();
		}		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getDataFull(String begin, String end) {
		return getDataFullDao(begin, end);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getDataByDate() {
		return getDataByDateDao();
	}
}
