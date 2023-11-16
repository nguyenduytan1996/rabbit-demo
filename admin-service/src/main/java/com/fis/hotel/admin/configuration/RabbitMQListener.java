package com.fis.hotel.admin.configuration;

import org.springframework.stereotype.Component;

import com.fis.ihotelframework.dto.Notification;
import com.google.gson.Gson;

@Component
public class RabbitMQListener {

//	public void listen(byte[] message) {
//        String msg = new String(message);
//        Notification not = new Gson().fromJson(msg, Notification.class);
//        System.out.println("Received a new notification...");
//        System.out.println(not.toString());
//    }
}
