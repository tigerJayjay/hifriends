package com.easybug.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerOri {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //ActiveMq 的默认登录密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //ActiveMQ 的链接地址
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    private AtomicInteger count = new AtomicInteger(0);
    ConnectionFactory connectionFactory;
    Connection connection;

    Session session;

    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();
    public void init(){
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true,Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String disname){
        try {
            //创建队列
            Queue queue = session.createQueue(disname);

            MessageProducer messageProducer = null;
            if(threadLocal.get()!=null){
                messageProducer = threadLocal.get();
            }else{
                messageProducer = session.createProducer(queue);
                threadLocal.set(messageProducer);
            }
            while(true){
                try {
                    Thread.sleep(1000);
                    int num =  count.getAndIncrement();
                    TextMessage msg = session.createTextMessage(Thread.currentThread().getName()+"生产东西"+num);
                    messageProducer.send(msg);
                    session.commit();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
