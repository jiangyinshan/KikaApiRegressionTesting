package TestCase;

import AssertImpl.CommanResponseCheckImpl;
import AssertImpl.ResourceArrayCheckImpl;
import AssertInterface.ResourceArrayCheck;
import Util.GetParamsArray;
import Util.LogUtil;
import Util.RequestConstructer;
import Util.CsvAction;
import com.opencsv.exceptions.CsvMalformedLineException;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * Branded tab页接口
 **/
public class BrandedTabCase implements GetParamsArray, ResourceArrayCheck {
    public static Log log = LogFactory.getLog(PopupTagCase.class.getName());
    private String[] paramsArray;//存储从csv文件中读取的此用例对应的数据，存储为字符串数组
    public int line;//参数在csv文件行数在csvList中的index
    private final String apiName = "branded tab页接口";//当前测试类在csv文件中的名称，用于遍历csv文件时找到对应名称的那一行


    @Test
    public void TestCase() throws IOException {
        getCsvParams(apiName, paramsArray);
        Request request = RequestConstructer.getInstance().ConstructGetRequest(paramsArray);
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        String responseStr = response.body().string();
        CommanResponseCheckImpl.getInstance().CheckResponseFormat(line, response, responseStr);
        Check(line, responseStr, ResourceArrayCheckImpl.getInstance().theme_list);
    }

    @Override
    public void getCsvParams(String apiName, String[] paramsArray) throws CsvMalformedLineException {
        for (String[] s : CsvAction.getInstance().getCSVDataList()) {
            if (s[8].contains(apiName)) {
                this.paramsArray = s;
                break;
            } else {
                this.line = this.line + 1;
            }
        }
        if (this.paramsArray == null) {
            LogUtil.apiNotFound(this.apiName);
        }
    }

    @Override
    public boolean Check(int line, String responseStr, String param) {
        ResourceArrayCheckImpl.getInstance().Check(line, responseStr, param);
        return true;
    }
}
