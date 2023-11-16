package com.fis.hotel.admin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;
    
	@Value("${redis.password}")
	private String redisPassWord;
    
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        // Tạo Standalone Connection tới Redis
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
//    }
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
    	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisHost);
		redisStandaloneConfiguration.setPort(redisPort);
		redisStandaloneConfiguration.setPassword(redisPassWord);
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return lettuceConnectionFactory;
    }

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {      
    	RedisTemplate<Object, Object> template = new RedisTemplate<>();

		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		FastJsonRedisSerializer<String> fastJsonRedisSerializer = new FastJsonRedisSerializer<String>(String.class);

		//
		template.setKeySerializer(redisSerializer);
		template.setHashKeySerializer(redisSerializer);
		//
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		//
		template.setConnectionFactory(redisConnectionFactory);

		return template;
    }
}
