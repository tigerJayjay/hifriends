package com.easybug.netty.server;

import com.easybug.common.SysException;
import com.easybug.netty.server.handler.ComedyHandler;
import com.easybug.netty.server.handler.ServerMessageReceHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ServerInit implements Runnable{
    private Logger logger = LoggerFactory.getLogger(ServerInit.class);
    private String host;
    private int port;
    public ServerInit(String host,int port){
        this.host = host;
        this.port = port;
    }
    @Override
    public void run(){
        logger.info("netty服务端开始初始化!");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ObjectDecoder(1024*2048, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                        socketChannel.pipeline().addLast(new ObjectEncoder());
                        socketChannel.pipeline().addLast(new IdleStateHandler(0,0,60));
                        socketChannel.pipeline().addLast(new ComedyHandler());
                        socketChannel.pipeline().addLast(new ServerMessageReceHandler());
                    }
                }).childOption(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.SO_BACKLOG,128);
        try {
            ChannelFuture f = serverBootstrap.bind(new InetSocketAddress(host,port)).sync();
            logger.info("netty服务端初始化完成!");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new SysException("netty服务端初始化异常"+this.getClass(),500);
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
