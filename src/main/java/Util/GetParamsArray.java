package Util;

import com.opencsv.exceptions.CsvMalformedLineException;

public interface GetParamsArray {
    /**
     * 遍历csv文件参数List，根据apiName找到对应的参数的字符串数组，返回给接口测试类的paramArray参数数组
     *
     * @param apiName     接口的名称，对应csv文件第八列
     * @param paramsArray 接口测试类对应接口的参数数组
     **/
    void getCsvParams(String apiName, String[] paramsArray) throws CsvMalformedLineException;
}
