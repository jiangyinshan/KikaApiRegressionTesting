package TestCase;

import AssertImpl.CommanResponseCheckImpl;
import AssertImpl.ParamExistCheckImpl;
import AssertImpl.ResourceArrayCheckImpl;
import AssertInterface.ParamExistCheck;
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
 * theme推荐接口
 **/
public class PreResourceLauncherCase implements GetParamsArray, ParamExistCheck {
    public static Log log = LogFactory.getLog(PopupTagCase.class.getName());
    private String[] paramsArray;
    public int line;//参数在csv文件行数在csvList中的index
    private final String apiName = "pre launcher详情页接口";
    private final String[] responseParamArray = {"preview","extra_image_googleplay2","extra_image_googleplay3","imgPreviewGif","icon","description","priority","url","pkg_name","download_url","name","imgGif","key","subScript"};

    @Test
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
        ParamExistCheckImpl.getInstance().Check(line,responseStr,paramArray);
        return false;
    }
}
