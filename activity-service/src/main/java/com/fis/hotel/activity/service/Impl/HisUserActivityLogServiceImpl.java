package com.fis.hotel.activity.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fis.hotel.activity.configuration.RabbitMQProperties;
import com.fis.hotel.activity.dao.HisUserActivityLogDao;
import com.fis.hotel.activity.service.HisUserActivityLogService;
import com.fis.hotel.activity.service.RestService;
import com.fis.ihotelframework.dto.HisDiffsChangeDataDto;
import com.fis.ihotelframework.dto.MessagePublishRabbit;
import com.fis.ihotelframework.model.HisUserActivityLog;

import fis.core.jdbc.exception.TransactionException;

@Service
public class HisUserActivityLogServiceImpl extends HisUserActivityLogDao implements HisUserActivityLogService {

	@Autowired
	RestService sysMessageService;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	RabbitMQProperties rabbitMQProperties;

	@Override
	public boolean createOrUpdate(String schema, String propertyCode, HisUserActivityLog model) {
		try {
			MessagePublishRabbit<HisUserActivityLog> body = new MessagePublishRabbit<HisUserActivityLog>();
			body.setObjectBody(model);
			body.setSchema(schema);
			body.setPropertyCode(propertyCode);
			amqpTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), "queue.hisUserActivityLog", body);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

//	@Override
//	@Transactional
//	public boolean createOrUpdate(String schema, String propertyCode, HisUserActivityLog model) {
//		try {
//			if (save(schema, model).getId() != null) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception ex) {
//			return false;
//		}
//	}

	@RabbitListener(queues = "${skybeds.rabbitmq.queueName}")
	@Transactional(rollbackFor = TransactionException.class)
	public void createOrUpdateListener(MessagePublishRabbit<HisUserActivityLog> model) throws TransactionException {
		try {
			if (save(model.getSchema(), model.getObjectBody()).getId() != null) {
				System.out.printf("Success");
			} else {
				throw new TransactionException();
			}
		} catch (TransactionException ex) {
			throw new TransactionException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<HisUserActivityLog> findOne(String schema, Long id) {
		return findById(schema, id);
	}

	@Override
	@Transactional
	public void removeById(String schema, Long id, String language) {
		deleteById(schema, id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HisUserActivityLog> getSearchViewChange(String schema, String propertyCode, String typeCode,
			String user, Date fromDate, Date toDate, Long id) {
		return getSearchViewChangeDao(schema, propertyCode, typeCode, user, fromDate, toDate, id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HisDiffsChangeDataDto> getSearchViewChangeTest(String schema, String propertyCode, String typeCode,
			String user, Date fromDate, Date toDate, Long id) throws JsonMappingException, JsonProcessingException {

		return getSearchViewChangeTestDao(schema, propertyCode, typeCode, user, fromDate, toDate, id);
	}
}
