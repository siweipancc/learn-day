package com.pancc.learn.log.sl4j.migrate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Siweipancc
 */
public class MigrateLog {

    public static void main(String[] args) {
        // JUL 重定向必须手动执行
        bridgeJul2Slf4();

        // 我们的 slf4j 调用
        Logger logger = LoggerFactory.getLogger(MigrateLog.class);
        logger.info("This log is generated by \n[{}] via \n[{}]", logger.getClass(), Logger.class);

        // CALL AnotherLog
        MigrateSubLog.mainRoute();
    }

    private static void bridgeJul2Slf4() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

}
