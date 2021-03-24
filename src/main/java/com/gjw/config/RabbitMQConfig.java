package com.gjw.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author gjw
 * @Date 2021/3/20 20:09
 **/
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

   @Value("${spring.rabbitmq.virtual-host}")
    private String virturalHost;

    @Autowired
    private RabbitMQConfirmCallback rabbitMQConfirmCallback;

    private RabbitMQReturnCallback rabbitMQReturnCallback;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virturalHost);

        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        factory.setPublisherReturns(true);

        return factory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setMandatory(true);

        template.setConfirmCallback(rabbitMQConfirmCallback);

        template.setReturnsCallback(rabbitMQReturnCallback);

        return template;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(@Autowired ConnectionFactory connectionFactory){
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        return  admin;
    }


    /**
     * 死信交换机
     * */
    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange("deadExchangeKey").build();
    }


}
