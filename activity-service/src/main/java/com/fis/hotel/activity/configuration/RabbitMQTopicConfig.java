package com.fis.hotel.activity.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class RabbitMQTopicConfig {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;
    
    @Bean
    TopicExchange deadLetterExchange() {
		return new TopicExchange(rabbitMQProperties.getDeadExchangeName());
	}
    
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(rabbitMQProperties.getExchangeName());
    }
    
    @Bean
	Queue dlq() {
		return QueueBuilder.durable(rabbitMQProperties.getDeadQueueName()).build();
	}
    
    @Bean
	Queue queue() {
		return QueueBuilder.durable(rabbitMQProperties.getQueueName()).withArgument("x-dead-letter-exchange", rabbitMQProperties.getDeadExchangeName())
				.withArgument("x-dead-letter-routing-key", "deadQueue.activity").build();
	}

//	@Bean
//	Queue queue() {
//		return new Queue(rabbitMQProperties.getQueueName(), false);
//	}
	
	@Bean
	Binding binding(Queue queue, TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with("queue.*");
	}
	
	@Bean
	Binding DLQbinding(Queue dlq, TopicExchange deadLetterExchange) {
		return BindingBuilder.bind(dlq).to(deadLetterExchange).with("deadQueue.activity");
	}

//	@Bean
//	DirectExchange exchange() {
//		return new DirectExchange(rabbitMQProperties.getExchangeName());
//	}

//	@Bean
//	Binding binding(Queue queue, DirectExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(rabbitMQProperties.getRoutingKey());
//	}

//	@Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(rabbitMQProperties.getQueueName());
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
	
//	@Bean
//    MessageListenerAdapter listenerAdapter(RabbitMQListener listener) {
//        return new MessageListenerAdapter(listener, "listen");
//    }

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
