package AssertImpl;


import AssertInterface.ResponseCheck;
import ResponseBean.ResponseBean;
import Util.CsvAction;

import com.alibaba.fastjson.JSON;
import com.opencsv.exceptions.CsvMalformedLineException;
import okhttp3.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class CommanResponseCheckImpl implements ResponseCheck {

    public static Log log = LogFactory.getLog(CommanResponseCheckImpl.class.getName());
    private volatile static CommanResponseCheckImpl instance;

    public static CommanResponseCheckImpl getInstance() {
        if (instance == null) {
            synchronized (CommanResponseCheckImpl.class) {
                if (instance == null) {
                    instance = new CommanResponseCheckImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean CheckResponseFormat(int line, Response response, String responseBodyStr) throws IOException {
        System.out.println(responseBodyStr);
        ResponseBean responseBean = JSON.parseObject(responseBodyStr, ResponseBean.class);
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
