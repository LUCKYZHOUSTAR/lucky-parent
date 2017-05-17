package lucky.util.log.lockback;

import lucky.util.log.Level;
import lucky.util.log.Logger;
import lucky.util.log.LoggerAdapter;

import java.io.File;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:41 2017/5/17
 */
public class LogBackLoggerAdapter implements LoggerAdapter {
    private File file;

    @Override
    public Logger getLogger(Class<?> key) {
        return new LogBackLogger(new ch.qos.logback.classic.LoggerContext().getLogger(key.getName()));
    }

    @Override
    public Logger getLogger(String key) {

        return new LogBackLogger(new ch.qos.logback.classic.LoggerContext().getLogger(key));
    }

    @Override
    public void setLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(toLogBackLevel(level));

    }

    @Override
    public Level getLevel() {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        return fromLogBackLevel(root.getLevel());

    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {

    }


    private static ch.qos.logback.classic.Level toLogBackLevel(Level level) {
        if (level == Level.ALL)
            return ch.qos.logback.classic.Level.ALL;
        if (level == Level.TRACE)
            return ch.qos.logback.classic.Level.TRACE;
        if (level == Level.DEBUG)
            return ch.qos.logback.classic.Level.DEBUG;
        if (level == Level.INFO)
            return ch.qos.logback.classic.Level.INFO;
        if (level == Level.WARN)
            return ch.qos.logback.classic.Level.WARN;
        if (level == Level.ERROR)
            return ch.qos.logback.classic.Level.ERROR;
        // if (level == Level.OFF)
        return ch.qos.logback.classic.Level.OFF;
    }


    private static Level fromLogBackLevel(ch.qos.logback.classic.Level level) {
        if (level == ch.qos.logback.classic.Level.ALL)
            return Level.ALL;
        if (level == ch.qos.logback.classic.Level.TRACE)
            return Level.TRACE;
        if (level == ch.qos.logback.classic.Level.DEBUG)
            return Level.DEBUG;
        if (level == ch.qos.logback.classic.Level.INFO)
            return Level.INFO;
        if (level == ch.qos.logback.classic.Level.WARN)
            return Level.WARN;
        if (level == ch.qos.logback.classic.Level.ERROR)
            return Level.ERROR;
        // if (level == ch.qos.logback.classic.Level.OFF)
        return Level.OFF;
    }
}
