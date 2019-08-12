package com.easybug.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerOri {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //ActiveMq 的默认登录密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //ActiveMQ 的链接地址
    //prefetchAck为true,prefetchSize必须大于0，当prefetchAck为false,prefetchSize>=0，
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL+"?jms.optimizeAcknowledge=true&jms.optimizeAcknowledgeTimeOut=30000";

    private AtomicInteger count = new AtomicInteger(0);
    ConnectionFactory connectionFactory;
    Connection connection;

    Session session;

    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
    public void init(){
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void getMessage(String disname){
        try {
            //创建队列
            Queue queue = session.createQueue(disname+"?customer.prefetchSize=100");

            MessageConsumer messageConsumer = null;
            if(threadLocal.get()!=null){
                messageConsumer = threadLocal.get();
            }else{
                messageConsumer = session.createConsumer(queue);
                threadLocal.set(messageConsumer);
            }
            while(true){
                try {
                    Thread.sleep(1000);
                    int num =  count.getAndIncrement();
                    TextMessage msg = (TextMessage)messageConsumer.receive();
                    if(msg!=null){
                        msg.acknowledge();
                        System.out.println(msg.getText());
                    }else{
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
