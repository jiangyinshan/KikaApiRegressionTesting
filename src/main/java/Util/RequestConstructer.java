package Util;

import okhttp3.*;
import okio.Buffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestConstructer {
    public static final String likeThemeJson = "{\n" +
            "\t\"theme_list\": [{\n" +
            "\t\t\"key\": \"vcxpDgqw5w0G\",\n" +
            "\t\t\"likedTime\": 1620379450332,\n" +
            "\t\t\"pkg_name\": \"com.ikeyboard.theme.pink.rose.flower\",\n" +
            "\t\t\"preview\": \"http://cdn.kikakeyboard.com/terminal/online/compress/20200602/fde88bd4-a4a5-11ea-95d8-061493b0cc7d.jpg.webp\",\n" +
            "\t\t\"vip\": 0\n" +
            "\t}]\n" +
            "}";
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
    public Request ConstructPostRequest(String[] paramsArray) throws IOException {
        if (paramsArray.length < 1) {
            log.fatal("参数错误，请检查参数配置文件");
        }
        if (!CheckValidity(paramsArray)) {
            log.fatal("字段个数和value个数不匹配");
        }
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
            if (parameterValue[i].equals("none")) {
                paramMap.put(parameterName[i], "");
            } else {
                paramMap.put(parameterName[i], parameterValue[i]);
            }
        }
        Request request = httpPost(SplitURL(paramsArray), headMap, paramMap);
        return request;
    }

    public Request ConstructGetRequest(String[] paramsArray) {
        if (paramsArray.length < 1) {
            log.fatal("参数错误，请检查参数配置文件");
        }
        if (!CheckValidity(paramsArray)) {
            log.fatal("字段个数和value个数不匹配");
        }
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
     * 未使用,构造json格式请求
     **/
    public Request ConstructJsonRequest(String[] paramsArray) throws IOException {
        if (paramsArray.length < 1) {
            log.fatal("参数错误，请检查参数配置文件");
        }
        if (!CheckValidity(paramsArray)) {
            log.fatal("字段个数和value个数不匹配");
        }
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
            if (parameterValue[i].equals("none")) {
                paramMap.put(parameterName[i], "");
            } else {
                paramMap.put(parameterName[i], parameterValue[i]);
            }
        }
        Request request = httpPost(SplitURL(paramsArray), headMap, paramMap);
        return request;
    }

    /**
     * 组装GET request对象
     **/
    public Request httpGet(String url, HashMap<String, String> headMap, HashMap<String, String> params) {
        // 设置HTTP请求参数
        String result = null;
        url += getParams(params);
        Headers setHeaders = SetHeaders(headMap);
        Request request = new Request.Builder().url(url).headers(setHeaders).build();
        return request;
    }

    /**
     * 组装POST request对象
     **/
    public Request httpPost(String url, HashMap<String, String> headMap, HashMap<String, String> bodysMap) throws IOException {
        // 设置HTTP请求参数
        RequestBody requestBody = SetBody(bodysMap);
        Headers setHeaders = SetHeaders(headMap);
        Request request = new Request.Builder().url(url).headers(setHeaders).post(requestBody).build();
        return request;
    }

    /**
     * 构造MultipartBody
     **/
    public MultipartBody SetBody(HashMap<String, String> bodysMap) throws IOException {
        MultipartBody body = null;
        MultipartBody.Builder bodybuilder = new MultipartBody.Builder();
        if (!EmptyUtil.isNullOrEmpty(bodysMap)) {
            Iterator<String> iterator = bodysMap.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                bodybuilder.addFormDataPart(key, bodysMap.get(key));
            }
        }
        body = bodybuilder.build();
        PrintMultipartBody(body);
        return body;
    }

    /**
     * 构造Headers
     **/
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

    /**
     * 拼接url后请求参数字符串：如   url?key=value&key=value
     **/
    public String getParams(HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder("?");
        if (!EmptyUtil.isNullOrEmpty(params)) {
            for (Map.Entry<String, String> item : params.entrySet()) {
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

    /**
     * 输出MultipartBody信息
     **/
    public static void PrintMultipartBody(MultipartBody body) throws IOException {
        Map<String, String> params = new HashMap<>();
        Map<String, String> files = new HashMap<>();
        for (MultipartBody.Part part : body.parts()) {
            RequestBody body1 = part.body();
            Headers headers = part.headers();
            if (headers != null && headers.size() > 0) {
                String[] split = headers.value(0).replace(" ", "").replace("\"", "").split(";");
                if (split.length == 2) {
                    //文本
                    String[] keys = split[1].split("=");
                    if (keys.length > 1 && body1.contentLength() < 1024) {
                        String key = keys[1];
                        String value = "";
                        Buffer buffer = new Buffer();
                        body.writeTo(buffer);
                        value = buffer.readUtf8();
                        params.put(key, value);
                    }
                } else if (split.length == 3) {
                    //文件
                    String fileKey = "";
                    String fileName = "";
                    String[] keys = split[1].split("=");
                    String[] names = split[2].split("=");
                    if (keys.length > 1) fileKey = keys[1];
                    if (names.length > 1) fileName = names[1];
                    files.put(fileKey, fileName);
                }
            }

        }
        System.out.println("文本参数-->" + params);
        System.out.println("文件参数-->" + files);
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

    /**
     * 验证csv文件配置格式是否正确
     **/
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

    /**
     * 移除字符串中特殊字符
     **/
    public String RemoveSpecialSymbol(String s) {
        //可以在中括号内加上任何想要替换的字符
        String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        String aa = "";//这里是将特殊字符换为aa字符串,""代表直接去掉
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);//这里把想要替换的字符串传进来
        String newString = m.replaceAll(aa).trim(); //将替换后的字符串存在变量newString中
        return newString;
    }

    /**
     * 将数组格式字符串转为字符串数组
     **/
    public static String[] toStringArray(String source) {
        return source.substring(1, source.length() - 1).split(",");
    }

    /**
     * 读取csv文件第5列请求头参数字段
     **/
    public String[] ReadHeaderName(String[] paramsArray) {
        return toStringArray(paramsArray[4]);
    }

    /**
     * 读取csv文件第6列请求头参数字段的值
     **/
    public String[] ReadHeaderValue(String[] paramsArray) {
        return toStringArray(paramsArray[5]);
    }

    /**
     * 读取csv文件第7列请求体参数字段
     **/
    public String[] ReadParameterName(String[] paramsArray) {
        return toStringArray(paramsArray[6]);
    }

    /**
     * 读取csv文件第8列请求体参数字段的值
     **/
    public String[] ReadParameterValue(String[] paramsArray) {
        return toStringArray(paramsArray[7]);
    }

}
