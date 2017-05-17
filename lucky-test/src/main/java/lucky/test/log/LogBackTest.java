package lucky.test.log;


import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:40 2017/5/17
 */
public class LogBackTest {
    static {
        System.setProperty("com.lucky.logger", "slf4j");
    }


    //todo:还是得用相应的logger信息
//    protected static final Logger logger = LoggerFactory.getLogger(LogBackTest.class);
    public static Logger logger = LoggerFactory.getLogger(LogBackTest.class);
    public static org.slf4j.impl.StaticLoggerBinder defaultLoggerContext = org.slf4j.impl.StaticLoggerBinder.getSingleton();
//

    public static ch.qos.logback.classic.Logger logger2 = new ch.qos.logback.classic.LoggerContext().getLogger(LogBackTest.class);

    public static void main(String[] args) {

        logger.info("logback日志类型测试信息");
        logger.error("logback日志类型输出文件");

    }
}
