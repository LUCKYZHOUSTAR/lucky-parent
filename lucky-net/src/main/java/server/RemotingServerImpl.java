package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import server.handler.RemotingServerInitializer;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:netty服务端
 * @Date:Create in 16:26 2017/6/9
 */
public class RemotingServerImpl extends ServerBootstrap implements RemotingServer {
    private static final Logger logger = LoggerFactory.getLogger(RemotingServerImpl.class);
    private RemotingServerConfig remotingServerConfig;
    private EventLoopGroup workerGroup;
    private EventLoopGroup bossGroup;

    //负责io读写线程，否则会占用worker线程，如果解析慢得话，最好利用该线程池，处理业务不慢得话，最好不用，减少线程上下文的切换开销
    private DefaultEventExecutorGroup defaultEventExecutorGroup;


    public RemotingServerImpl(RemotingServerConfig config) {
        this.remotingServerConfig = config;
        init();
    }

    private void init() {
        this.workerGroup = new NioEventLoopGroup(remotingServerConfig.getBossThread(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, String.format("NettyWorkerSelector_%d",
                        this.threadIndex.getAndIncrement()));
            }
        });

        this.bossGroup = new NioEventLoopGroup(remotingServerConfig.getWorkThread(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyBossSelector_%d", this.threadIndex.getAndIncrement()));
            }
        });

        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(remotingServerConfig.getWorkSelectorThread(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("worderSelecotor_d%", this.threadIndex.getAndIncrement()));
            }
        });

    }


    @Override
    public void start() {
        this.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, remotingServerConfig.getBackLogRequest())
                .option(ChannelOption.SO_RCVBUF, remotingServerConfig.getReceiveBufferSize())
                .option(ChannelOption.SO_SNDBUF, remotingServerConfig.getSenBufferSize())
                .option(ChannelOption.TCP_NODELAY, remotingServerConfig.isTcpNoDelay());
        //这个选项会占用大量得堆外内存
        if (remotingServerConfig.isServerPooledByteBufAllocatorEnable()) {
            this.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }





    }

    @Override
    public void stop() {

    }


    public RemotingServerConfig getRemotingServerConfig() {
        return remotingServerConfig;
    }

    public void setRemotingServerConfig(RemotingServerConfig remotingServerConfig) {
        this.remotingServerConfig = remotingServerConfig;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public void setBossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public DefaultEventExecutorGroup getDefaultEventExecutorGroup() {
        return defaultEventExecutorGroup;
    }

    public void setDefaultEventExecutorGroup(DefaultEventExecutorGroup defaultEventExecutorGroup) {
        this.defaultEventExecutorGroup = defaultEventExecutorGroup;
    }
}
