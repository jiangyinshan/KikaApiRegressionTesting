package ResponseBean;

/**
 * Copyright yinshan.jiang
 */


import Util.CsvAction;
import Util.RequestConstructer;

/**
 * @website 接口响应pojo
 */
public class ResponseBean {

    private int errorCode;
    private String errorMsg;
    private String data;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
