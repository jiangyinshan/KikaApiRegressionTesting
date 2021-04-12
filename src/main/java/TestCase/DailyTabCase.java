package TestCase;

import AssertImpl.CommanResponseCheck;
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
 * Daily tab页接口
 **/
public class DailyTabCase implements GetParamsArray {
    public static Log log = LogFactory.getLog(PopupTagCase.class.getName());
    private String[] paramsArray;
    public int line;//参数在csv文件行数在csvList中的index
    private final String apiName = "daily tab页接口";


    @Test
    public void TestCase() throws IOException {
        getCsvParams(apiName, paramsArray);
        Request request = RequestConstructer.getInstance().ConstructGetRequest(paramsArray);
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        CommanResponseCheck.getInstance().CheckResponseFormat(line, response);
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
}