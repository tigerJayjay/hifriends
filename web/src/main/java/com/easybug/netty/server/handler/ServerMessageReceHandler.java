package com.easybug.netty.server.handler;

import com.easybug.common.SysException;
import com.easybug.netty.protocal.Message;
import com.easybug.netty.protocal.Type;
import com.easybug.netty.server.ServerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ServerMessageReceHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(ServerMessageReceHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接: "+ctx.channel().remoteAddress());
        ServerManager.addOnline(ctx);
        Message m = new Message();
        m.setType(Type.Group);
        Map ma = new HashMap();
        ma.put("message","通知:"+ctx.channel().remoteAddress().toString().substring(1)+"加入了群聊");
        m.setAttachment(ma);
        ServerManager.sendMessageGoup(ctx,m);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开连接:"+ctx.channel().remoteAddress());
        ServerManager.removeClient(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("接受到客户端信息:"+msg.toString());
        Message message = (Message)msg;
        if(message.getType().equals(Type.Req)){
            message.setType(Type.Res);
            ctx.writeAndFlush(message);
        }else if(message.getType().equals(Type.HeatReq)){
             //接收到心跳包，返回响应
             message.setType(Type.HeatResp);
             ctx.writeAndFlush(message);
        }else if(message.getType().equals(Type.SendMessage)){
            ServerManager.sendMessageTo(message);
        }else if(message.getType().equals(Type.Group)){
            ServerManager.sendMessageGoup(ctx,message);
        }else if(message.getType().equals(Type.HeatResp)){
            logger.info("客户端心跳包响应");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ServerManager.ipChannelMap.remove(ctx.channel().remoteAddress());
        ctx.close();
        throw new SysException("服务器接受消息异常!",500);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent)evt;
            if(e.state()== IdleState.READER_IDLE){
                readIdleHandler(ctx);
            }
        }
    }

    private void readIdleHandler(ChannelHandlerContext ctx){
        ctx.close();
    }


}
