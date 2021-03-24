package com.gjw.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ confirmCallback
 * @Author gjw
 * @Date 2021/3/20 20:26
 **/
@Component
public class RabbitMQReturnCallback implements RabbitTemplate.ReturnsCallback {

    /**
     * 消息无法发送到交换机的 rabbitMQ的回调
     * */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        ObjectMapper o = new ObjectMapper();

        try {
            System.out.println("return-->"+o.writeValueAsString(returnedMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
