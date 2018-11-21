package com.example.websocket.controller;

import com.example.websocket.config.WebSocketConfig;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestHandler extends TextWebSocketHandler {
    private static final Map<String,Thread> map;
    static {
        map = new HashMap<String, Thread>();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        closeSession(session,session.getId());
    }

    public void closeSession(WebSocketSession session,String id){
       SocketThread thread =  (SocketThread) map.get(id);
       if(thread!=null){
           map.remove(id);
           thread.setFlag(false);
           System.out.println(id+"closed");
       }
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String param  = message.getPayload();
        if(param!=null && !"".equals(param)){
            if(map.containsKey(session.getId())){
                SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dataFormatter.format(new Date());
                System.out.println(date + session.getId()+" : Received client heartbeat......");
                return;
            }
            SocketThread thread = new SocketThread(session,param);
            map.put(session.getId(),thread);
            thread.setDaemon(true);
            thread.start();
        }
    }

    class  SocketThread extends  Thread{
        private boolean flag = true;
        private WebSocketSession session;
        private String param;

        public SocketThread(WebSocketSession session,String sid){
            this.session = session;
            this.param = sid;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            while (flag) {
                try {
                    session.sendMessage(new TextMessage(param));
                    Thread.sleep(1000);
                    //session.close();
                } catch(Exception e){
                    e.printStackTrace();
                } finally{
                }
            }
        }

        public String getValue(){
            return "test";
        }
    }
}
