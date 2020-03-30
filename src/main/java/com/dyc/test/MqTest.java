package com.dyc.test;

import com.dyc.rabbitmq.ProducerService;
import com.dyc.rabbitmq.MqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName MqTest
 * @Author lihaodong
 * @Date 2019/2/24 20:54
 * @Mail lihaodongmail@163.com
 * @Description mq测试
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTest {

    @Autowired
    private MqSender mqSender;

    @Autowired
    private ProducerService senderService;

    @Test
    public void send() {
        mqSender.send("helle dyc");
    }

    @Test
    public void sendTopic() {
        mqSender.sendTopic("helle dyc");
    }
    @Test
    public void sendWithConfirm() throws InterruptedException {
        senderService.sendMq();
    }

    @Test
    public void sendFanout() {
        mqSender.sendFanout("helle dyc");
    }

    @Test
    public void sendHeaders() {
        mqSender.sendHeaders("helle dyc");
    }

}
