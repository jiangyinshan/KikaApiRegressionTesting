package Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtil {
    public static Log log = LogFactory.getLog(LogUtil.class.getName());

    public static void printParameters(String apiName, String parameters) {
        log.info(apiName + "请求参数：" + parameters);
    }

    public static void apiNotFound(String apiName) {
        log.error("未找到该接口:" + apiName);
    }
}
