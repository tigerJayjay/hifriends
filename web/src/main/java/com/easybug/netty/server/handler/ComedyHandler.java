package com.easybug.netty.server.handler;

import com.easybug.netty.protocal.Message;
import com.easybug.netty.protocal.Type;
import com.easybug.netty.server.ServerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.*;

public class ComedyHandler extends ChannelInboundHandlerAdapter {
    private static List<String> l = new ArrayList();
    static{
        l.add("医院里，一孕妇难产，医生灵机一动，给她服用了益生菌");
        l.add("300斤的某女没看到她老公躺在沙发上，一屁股把他坐死了。法律并没有追究她的责任，因为法不责重");
        l.add("酒吧里跟一肌肉男起了冲突，对方要打我。我说：“你也不打听打听我是谁？”\n" +
                "\n" +
                "“你谁？”\n" +
                "“我是一坨屎啊大哥，你打我不怕脏了手吗？”");
        l.add("一老板玩小三玩腻，小三逼婚不成索要千万赔偿。\n" +
                "\n" +
                "老板冥思苦想，计上心来。他以提高文化水平为由，花几十万让她上了高端商学院。\n" +
                "班上权贵如云，没两个月，小三就另觅新欢，不理老板了。\n" +
                "为了保持清纯形象，小三给了老板100万封口费。\n" +
                "---吴秀波一定没看过这个故事。");
        l.add("男生一般都鼓励女生去做美甲，这样轻易不舍得挠他！");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        Message m = (Message)msg;
        if(m.getType().equals(Type.Group)){
            String s = (String)m.getAttachment().get("message");
            if(s!=null){
                if(s.indexOf("笑话")!=-1){
                    int size = l.size();
                    Random r = new Random();
                    int index = r.nextInt(size);
                    Message newM  = new Message();
                    newM.setType(Type.Group);
                    Map m1 = new HashMap();
                    m1.put("message","人工智障:"+l.get(index));
                    newM.setAttachment(m1);
                    ServerManager.sendMessageGoup(ctx,newM);
                }
            }
        }
        ctx.fireChannelRead(msg);
    }

}
