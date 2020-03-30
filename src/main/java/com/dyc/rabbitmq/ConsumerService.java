package com.dyc.rabbitmq;


import com.dyc.rabbitmq.config.RabbitMQConfig;
import com.dyc.rabbitmq.service.BusinessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitMQConfig.TOPIC_QUEUE2)
public class ConsumerService {

    @Autowired
    private BusinessService businessService;

    @RabbitHandler
    public void process(String messageStr, Channel channel, Message message) {
        System.out.println("queue2接收的消息为:" + messageStr);
        try {

            //通知服务器此消息已经被消费，可从队列删掉， 这样以后就不会重发，否则后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            //todo something
            businessService.doit();

            //callback    success  failed


//            //消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            //ack返回false，并重新回到队列
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            //丢弃这条消息
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
//            //拒绝消息
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}