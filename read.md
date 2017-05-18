# 日志
    1.日志层已经封装完毕


# 服务配置信息
    参考lark的实现，通过配置文件在项目启动的时候，把所有的配置信息一次性读入
    先不用考虑那么复杂，先把配置文件的标准给定义出来
    APP.conf:全局的默认配置，包括服务的默认注册地址、注册的方式等等
        <config>
            <setting key="rpc.register.ip" value=""></setting>
            <setting key="rpc.register.type" value=""></setting>
        </config>
    Server暴漏
    remoting.server.xml
        <servers>
            <server name="" register="true"  address=":8080" version="" description>
                <setting key="MaxConnections" value=""/>
                 <setting key="ServicePackage" value="cmc.approval.front.service.iface"/>
            </server>
        </servers>
    remoting.client.xml
         <client>
                    <server name=""  address=":8080" discovery="true">
                      
                    </server>
          </servers>
    
    
# 配置文件
    基本上先按照上面的格式进行封装操作
    
    
    
    
    
    
    
    
    
# 服务暴漏
    1.服务端的服务如何暴漏？
    2.客户端如何来获取服务端暴漏的接口信息
    2.需要思考动态的配置信息,也就是规划服务端和客户端的配置信息
    
    
    
    
    


# 参考gitlab的地址
    https://github.com/zhaoshiling1017?utf8=%E2%9C%93&tab=repositories&q=voyage&type=&language=