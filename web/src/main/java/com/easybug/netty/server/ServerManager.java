package com.easybug.netty.server;

import com.easybug.common.SysException;
import com.easybug.netty.protocal.Message;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class ServerManager {
     public static Map<Integer, ChannelHandlerContext> userChannelMap = new HashMap<Integer,ChannelHandlerContext>();
     public static Map<ChannelHandlerContext,Integer> channelUserMap = new HashMap<ChannelHandlerContext, Integer>();
     public static Map<String,ChannelHandlerContext> ipChannelMap = new HashMap<>();
     public static void sendMessageTo(Message m){
        if(ipChannelMap.containsKey(m.getDesId())){
            ChannelHandlerContext context = ipChannelMap.get(m.getDesId());
            context.channel().writeAndFlush(m);
        }else{
            Map resMap = new HashMap<>();
            resMap.put("message","对方不在线!");
            m.setAttachment(resMap);
            ChannelHandlerContext context = ipChannelMap.get(m.getSrcId());
            context.channel().writeAndFlush(m);
        }
     }

     public static void addOnline(ChannelHandlerContext context){
         if(context==null){
             throw new SysException("连接服务器异常!",500);
         }
         ipChannelMap.put(context.channel().remoteAddress().toString(),context);
     }

     public static void removeClient(ChannelHandlerContext context){
         if(context==null){
             throw new SysException("连接服务器异常!",500);
         }
         context.close();
         ipChannelMap.remove(context.channel().remoteAddress().toString());
     }
     public static void sendMessageGoup(ChannelHandlerContext ctx,Message message){
         for(String ip:ipChannelMap.keySet()){
             ipChannelMap.get(ip).writeAndFlush(message);
         }
     }

}
