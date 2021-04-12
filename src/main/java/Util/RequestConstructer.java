package Util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestConstructer {
    public static Log log = LogFactory.getLog(RequestConstructer.class.getName());
    private volatile static RequestConstructer instance;

    public static RequestConstructer getInstance() {
        if (instance == null) {
            synchronized (RequestConstructer.class) {
                if (instance == null) {
                    instance = new RequestConstructer();
                }
            }
        }
        return instance;
    }

    /**
     * 组装Post请求
     **/
    public Request ConstructPostRequest(String[] paramsArray) {
        if (paramsArray.length < 1) {
            log.fatal("参数错误，请检查参数配置文件");
        }
        HttpUrl httpUrl = HttpUrl.parse(SplitURL(paramsArray));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("sign", paramsArray[1])
                .addFormDataPart("file", "测试图片", RequestBody.create(MediaType.parse("multipart/form-data"), new File(paramsArray[2])))
                .build();
        Request request = new Request.Builder()
                .header("User-Agent", paramsArray[0])
                .url(httpUrl.toString())
                .post(requestBody)
                .build();
        return request;
    }

    public Request ConstructGetRequest(String[] paramsArray) {
        if (paramsArray.length < 1) {
            log.fatal("参数错误，请检查参数配置文件");
        }
        if (!CheckValidity(paramsArray)) {
            log.fatal("字段个数和value个数不匹配");
        }
        HttpUrl httpUrl = HttpUrl.parse(SplitURL(paramsArray));
        String[] headerName = ReadHeaderName(paramsArray);
        String[] headerValue = ReadHeaderValue(paramsArray);
        String[] parameterName = ReadParameterName(paramsArray);
        String[] parameterValue = ReadParameterValue(paramsArray);
        HashMap headMap = new HashMap();
        HashMap paramMap = new HashMap();
        for (int i = 0; i < headerName.length; i++) {
            headMap.put(headerName[i], headerValue[i]);
        }
        for (int i = 0; i < parameterName.length; i++) {
            System.out.println(parameterName[i] + ":" + parameterValue[i]);
            paramMap.put(parameterName[i], parameterValue[i]);
        }

        Request request = httpGet(SplitURL(paramsArray), headMap, paramMap);
        System.out.println(request);
        return request;
    }

    /**
     * 根据csv文件中参数拼接接口url
     **/
    public String SplitURL(String[] paramsArray) {
        if (paramsArray.length < 1) {
            log.fatal("url拼接失败，请检查参数配置文件");
        }
        String url = paramsArray[0] + "://" + paramsArray[1] + paramsArray[2];
        return url;
    }

    public boolean CheckValidity(String[] paramsArray) {
        if (ReadHeaderName(paramsArray).length != ReadHeaderValue(paramsArray).length) {
            log.fatal("Header字段数和Value数不匹配，请检查参数配置文件");
            return false;
        }
        if (ReadParameterName(paramsArray).length != ReadParameterValue(paramsArray).length) {
            log.fatal("Parameter字段数和Value数不匹配，请检查参数配置文件");
            return false;
        }
        return true;
    }

    public String RemoveSpecialSymbol(String s) {
        //可以在中括号内加上任何想要替换的字符
        String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        String aa = "";//这里是将特殊字符换为aa字符串,""代表直接去掉
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);//这里把想要替换的字符串传进来
        String newString = m.replaceAll(aa).trim(); //将替换后的字符串存在变量newString中
        return newString;
    }

    public static String[] toStringArray(String source) {
        return source.substring(1, source.length() - 1).split(",");
    }

    public String[] ReadHeaderName(String[] paramsArray) {
        return toStringArray(paramsArray[4]);
    }

    public String[] ReadHeaderValue(String[] paramsArray) {
        return toStringArray(paramsArray[5]);
    }

    public String[] ReadParameterName(String[] paramsArray) {
        return toStringArray(paramsArray[6]);
    }

    public String[] ReadParameterValue(String[] paramsArray) {
        return toStringArray(paramsArray[7]);
    }


    public Request httpGet(String url, HashMap<String, String> headMap, HashMap<String, Object> params) {
        // 设置HTTP请求参数
        String result = null;
        url += getParams(params);
        Headers setHeaders = SetHeaders(headMap);
        Request request = new Request.Builder().url(url).headers(setHeaders).build();
        return request;
    }

    public String getParams(HashMap<String, Object> params) {
        StringBuilder sb = new StringBuilder("?");
        if (!EmptyUtil.isNullOrEmpty(params)) {
            for (Map.Entry<String, Object> item : params.entrySet()) {
                Object value = item.getValue();
                if (!EmptyUtil.isNullOrEmpty(value)) {
                    sb.append("&");
                    sb.append(item.getKey());
                    sb.append("=");
                    sb.append(value);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    public Headers SetHeaders(HashMap<String, String> headersParams) {
        Headers headers = null;
        okhttp3.Headers.Builder headersbuilder = new okhttp3.Headers.Builder();
        if (!EmptyUtil.isNullOrEmpty(headersParams)) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }

}
