package AssertInterface;

public interface ParamExistCheck {
    /**
     * @param line        用例所在行数index
     * @param responseStr 接口响应字符串
     * @param paramArray  需要验证的响应data字段中是否存在的字段字符串数组
     **/
    boolean Check(int line, String responseStr, String[] paramArray);
}
