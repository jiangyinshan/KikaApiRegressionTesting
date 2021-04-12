package AssertImpl;


import AssertInterface.ResponseCheck;
import ResponseBean.ResponseBean;
import Util.RequestConstructer;
import Util.CsvAction;

import com.alibaba.fastjson.JSON;
import com.opencsv.exceptions.CsvMalformedLineException;
import okhttp3.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class CommanResponseCheck implements ResponseCheck {
    final int responseCodeColumn = 9;
    final int errorCodeColumn = 10;
    final int errorMsgColumn = 11;
    final int dataColumn = 12;
    final int conclusionColumn = 13;
    public static Log log = LogFactory.getLog(CommanResponseCheck.class.getName());
    private volatile static CommanResponseCheck instance;

    public static CommanResponseCheck getInstance() {
        if (instance == null) {
            synchronized (RequestConstructer.class) {
                if (instance == null) {
                    instance = new CommanResponseCheck();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean CheckResponseFormat(int line, Response response) throws IOException {
        String s = response.body().string();
        System.out.println(s);
        ResponseBean responseBean = JSON.parseObject(s, ResponseBean.class);
        RecordContent(line, response.code(), responseBean.getErrorCode(), responseBean.getErrorMsg(), responseBean.getData());
        if (response.code() != 200 || responseBean.getErrorCode() != 0 || !responseBean.getErrorMsg().equals("ok")) {
            CsvAction.getInstance().RecordConclusion(line, false);
        } else {
            CsvAction.getInstance().RecordConclusion(line, true);
        }
        return true;
    }

    @Override
    public boolean RecordContent(int line, int responseCode, int errorCode, String errorMsg, String data) throws CsvMalformedLineException {
        CsvAction.getInstance().RecordResponseCode(line, String.valueOf(responseCode));
        CsvAction.getInstance().RecordErrorCode(line, String.valueOf(errorCode));
        CsvAction.getInstance().RecordErrorMsg(line, errorMsg);
        CsvAction.getInstance().RecordData(line, data);
        return true;
    }
}
