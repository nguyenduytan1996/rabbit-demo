package com.fis.hotel.admin.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "skybeds.rabbitmq")
public class RabbitMQProperties {

	private String queueName;

	private String exchangeName;

	private String deadQueueName;
	
	private String deadExchangeName;
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getDeadQueueName() {
		return deadQueueName;
	}

	public void setDeadQueueName(String deadQueueName) {
		this.deadQueueName = deadQueueName;
	}

	public String getDeadExchangeName() {
		return deadExchangeName;
	}

	public void setDeadExchangeName(String deadExchangeName) {
		this.deadExchangeName = deadExchangeName;
	}

}
