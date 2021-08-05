package TestCase;

import AssertImpl.CommanResponseCheckImpl;
import AssertImpl.ParamExistCheckImpl;
import AssertInterface.ParamExistCheck;
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
 * theme推荐接口
 **/
public class SomeOneThemeDetailCase implements GetParamsArray, ParamExistCheck {
    public static Log log = LogFactory.getLog(PopupTagCase.class.getName());
    private String[] paramsArray;
    public int line;//参数在csv文件行数在csvList中的index
    private final String apiName = "某个theme详情页接口";
    private final String[] responseParamArray = {"preview", "apk7z_url","previewCompress","icon","description","show_country","noadZipSize","download_url","id","vip","carousel_icon","key","apk_url","pushIcon","author","priority","zip_url","url","pkg_name","start_num","show_version","size","name","raw_zip_url"};


    public void TestCase() throws IOException {
        getCsvParams(apiName, paramsArray);
        Request request = RequestConstructer.getInstance().ConstructGetRequest(paramsArray);
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        String responseStr = response.body().string();
        CommanResponseCheckImpl.getInstance().CheckResponseFormat(line, response, responseStr);
        Check(line,responseStr,responseParamArray);
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
    public boolean Check(int line, String responseStr, String[] paramArray) {
        ParamExistCheckImpl.getInstance().Check(line, responseStr, paramArray);
        return false;
    }
}
