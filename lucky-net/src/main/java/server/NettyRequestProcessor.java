package server;

import data.NettyRequest;
import data.NettyResponse;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author:chaoqiang.zhou
 * @Description:业务处理类操作
 * @Date:Create in 16:18 2017/6/14
 */
public interface NettyRequestProcessor {

    NettyResponse processRequest(ChannelHandlerContext ctx, NettyRequest request);
}
