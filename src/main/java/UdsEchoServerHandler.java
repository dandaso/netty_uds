import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UdsEchoServerHandler extends ChannelInboundHandlerAdapter { 

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { 
        ctx.write(msg);
        ctx.flush(); 
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
        cause.printStackTrace();
        ctx.close();
    }
}

