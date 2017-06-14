package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import protocol.ServerDecoder;
import protocol.ServerEncoder;
import server.RemotingServerConfig;
import server.RemotingServerImpl;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 22:49 2017/6/9
 */
public class RemotingServerInitializer extends ChannelInitializer<Channel> {

    private static Logger logger = LoggerFactory.getLogger(RemotingServerInitializer.class);
    private RemotingServerImpl remotingServer;

    private static final String IDLE_STATE = "IDLE_STATE";
    private static final String SESSION_HANDLSR = "SESSION_HANDLER";
    private static final String SERVER = "SERVER";
    private static final String ENCODER = "ENCODER";
    private static final String DECODER = "DECODER";

    public RemotingServerInitializer(RemotingServerImpl remotingServer) {
        this.remotingServer = remotingServer;
    }

    //初始化channel操作,增加编码和解码
    @Override
    protected void initChannel(Channel channel) throws Exception {
        logger.debug("initialze channel");
        ConnectionHandler sessionHander = remotingServer.getSessionHandler();

        RemotingServerConfig config = remotingServer.getRemotingServerConfig();
        if (sessionHander.getChannels().size() >= remotingServer.getRemotingServerConfig().getMaxClients()) {
            logger.warn("reach max clients {},close client connection", remotingServer.getRemotingServerConfig().getMaxClients());
            channel.close();
            return;
        }
        IdleHandler idleHandler = new IdleHandler(config.getReadTimeOut(), config.getWriteTimeOut(), config.getKeepAliveTime());

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(remotingServer.getDefaultEventExecutorGroup());
        pipeline.addLast(IDLE_STATE, idleHandler);
        pipeline.addLast(SESSION_HANDLSR, sessionHander);
        pipeline.addLast(DECODER, new ServerDecoder());
        pipeline.addLast(SERVER, new ServerHandler(this.remotingServer));
        pipeline.addLast(ENCODER, new ServerEncoder());

    }
}