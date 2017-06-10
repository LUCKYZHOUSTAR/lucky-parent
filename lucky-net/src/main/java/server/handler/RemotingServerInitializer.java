package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import server.RemotingServerImpl;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 22:49 2017/6/9
 */
public class RemotingServerInitializer extends ChannelInitializer<Channel> {


    public final static String IDEAL_HANLDER = "idle_handler";
    private RemotingServerImpl remotingServer;

    public RemotingServerInitializer(RemotingServerImpl remotingServer) {
        this.remotingServer = remotingServer;
    }

    //初始化channel操作
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(remotingServer.getDefaultEventExecutorGroup());
    }
}
