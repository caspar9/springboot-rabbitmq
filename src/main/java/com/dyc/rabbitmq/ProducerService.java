package com.dyc.rabbitmq;

import com.dyc.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMq() throws InterruptedException {
        this.rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData("全局msgId");
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, "topic.key2", "dyc", correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("=====收到======");
        if (ack) {
            //将消息状态改为 send
            System.out.println("消息: " + correlationData + "，已经被ack成功");
        } else {
            System.out.println("消息: " + correlationData + "，nack，失败原因是：" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("sender return" + message.toString());
    }

//    如果消息没有到exchange,则confirm回调,ack=false
//    如果消息到达exchange,则confirm回调,ack=true
//    exchange到queue成功,则不回调return
//    exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)

}