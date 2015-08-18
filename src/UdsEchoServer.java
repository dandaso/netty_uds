import java.net.SocketAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerDomainSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;

public class UdsEchoServer {

    public void run() throws Exception {
        EventLoopGroup bossGroup = new EpollEventLoopGroup(); 
        EventLoopGroup workerGroup = new EpollEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
                                    
            b.group(bossGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 4096)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .channel(EpollServerDomainSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() { 
                        @Override
                        public void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new UdsEchoServerHandler());
                        }
                    });
 
            SocketAddress s = new DomainSocketAddress("/tmp/netty.sock");
            ChannelFuture f = b.bind(s);
            
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new UdsEchoServer().run();
    }
}


