package com.sm.pfprod.web.security;

import org.apache.log4j.Logger;

import java.text.MessageFormat;

/**
 * @ClassName: LoggerUtils
 * @Description: 日志打印
 */
public class LoggerUtils {

    /**
     * <p>生成调试级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成debug级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     *
     * @param logger     logger引用
     * @param template   带参数的日志模板
     * @param parameters 参数集合
     */
    public static void debug(Logger logger, String template, Object... parameters) {
        if (logger.isDebugEnabled()) {
            logger.debug(addUniqueId(MessageFormat.format(template, parameters)));
        }
    }

    /**
     * <p>生成通知级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成info级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     *
     * @param logger     logger引用
     * @param template   带参数的日志模板
     * @param parameters 参数集合
     */
    public static void info(Logger logger, String template, Object... parameters) {
        if (logger.isInfoEnabled()) {
            logger.info(addUniqueId(MessageFormat.format(template, parameters)));
        }
    }

    /**
     * <p>生成警告级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成warn级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     *
     * @param logger     logger引用
     * @param template   带参数的日志模板
     * @param parameters 参数集合
     */
    public static void warn(Logger logger, String template, Object... parameters) {
    	logger.warn(addUniqueId(MessageFormat.format(template, parameters)));
    }

    /**
     * <p>生成警告级别日志</p>
     * <p>带异常堆栈</p>
     *
     * @param throwable
     * @param logger
     * @param template
     * @param parameters
     */
    public static void warn(Throwable throwable, Logger logger, String template, Object... parameters) {
    	logger.warn(addUniqueId(MessageFormat.format(template, parameters)), throwable);
    }

    /**
     * <p>生成错误级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成error级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     *
     * @param throwable          错误异常堆栈
     * @param logger     logger引用
     * @param template   带参数的日志模板
     * @param parameters 参数集合
     */
    public static void error(Throwable throwable, Logger logger, String template, Object... parameters) {
    	logger.error(addUniqueId(MessageFormat.format(template, parameters)), throwable);
    }

    /**
     * <p>生成错误级别日志</p>
     * <p>
     * 根据带参数的日志模板和参数集合，生成error级别日志
     * 带参数的日志模板中以{数字}表示待替换为变量的日志点，如a={0}，表示用参数集合中index为0的参数替换{0}处
     * </p>
     *
     * @param logger     logger引用
     * @param template   带参数的日志模板
     * @param parameters 参数集合
     */
    public static void error(Logger logger, String template, Object... parameters) {
        logger.error(addUniqueId(MessageFormat.format(template, parameters)));
    }

    /**
     * 唯一ID添加
     *
     * @param msg
     * @return
     */
    private static String addUniqueId(String msg) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-").append(msg);
        return stringBuilder.toString();
    }

}
