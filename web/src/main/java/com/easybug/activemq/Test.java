package com.easybug.activemq;

import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;

public class Test {
  /*  public static  void main(String[] args){
        ProducerOri producerOri = new ProducerOri();
        producerOri.init();
        Test testMq = new Test();
        //Thread 1
        new Thread(testMq.new ProductorMq(producerOri)).start();
        //Thread 2
        new Thread(testMq.new ProductorMq(producerOri)).start();
        //Thread 3
        new Thread(testMq.new ProductorMq(producerOri)).start();
    }

    private class ProductorMq implements Runnable{
        ProducerOri producter;
        public ProductorMq(ProducerOri producter){
            this.producter = producter;
        }

        @Override
        public void run() {
            while(true){
                try {
                    producter.sendMessage("Jaycekon-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/


    public static  void main(String[] args){
        ConsumerOri consumerOri = new ConsumerOri();
        consumerOri.init();
        Test testMq = new Test();
        //Thread 1
        new Thread(testMq.new ConsumerMq(consumerOri)).start();
        //Thread 2
        new Thread(testMq.new ConsumerMq(consumerOri)).start();
        //Thread 3
        new Thread(testMq.new ConsumerMq(consumerOri)).start();
    }

    private class ConsumerMq implements Runnable{
        ConsumerOri consumerOri;
        public ConsumerMq(ConsumerOri consumerOri){
            this.consumerOri = consumerOri;
        }

        @Override
        public void run() {
            while(true){
                try {
                    consumerOri.getMessage("Jaycekon-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
