package rpc;

import client.ClientChanelPoolFactory;
import client.ClientChannelPool;
import client.ClientOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:创建远程调用invoker信息
 * @Date:Create in 17:05 2017/6/15
 */
public class InvokerFactory {
    private static final ClientChanelPoolFactory poolMap = new ClientChanelPoolFactory();

    private InvokerFactory(){
        //单例模式
    }


    public static RpcInvoker get(ClientOptions options){

        ClientChannelPool pool=poolMap.get(options);
        return new RpcInvoker(pool,options);
    }
}
