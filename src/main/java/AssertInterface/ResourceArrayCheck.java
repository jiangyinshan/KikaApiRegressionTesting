package AssertInterface;

import ResponseBean.ResponseBean;

/**
 * 业务逻辑校验接口
 **/
public interface ResourceArrayCheck {
    /**
     * @param line        用例所在行数index
     * @param responseStr 接口响应字符串
     * @param param       响应数据中data json对象中的字段名称
     **/
    boolean Check(int line, String responseStr, String param);
}
