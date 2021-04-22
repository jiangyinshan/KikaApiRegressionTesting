package AssertImpl;

import AssertInterface.ParamExistCheck;
import Util.CsvAction;
import Util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ParamExistCheckImpl implements ParamExistCheck {
    private volatile static ParamExistCheckImpl instance;

    public static ParamExistCheckImpl getInstance() {
        if (instance == null) {
            synchronized (ParamExistCheckImpl.class) {
                if (instance == null) {
                    instance = new ParamExistCheckImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 为兼容Notification接口，此接口为格式与其他接口不一致，导致无法转换成JSONObject；
     * 当捕获到转换异常时,先将data字段的数据转换成JSONArray，然后将JSONArray对象中元素转换成JSONObject
     **/
    @Override
    public boolean Check(int line, String responseStr, String[] paramArray) {
        JSONObject responseObject = JSON.parseObject(responseStr);
        JSONObject dataObject = JSONObject.parseObject(String.valueOf(responseObject));
        try {
            JSONObject paramListObject = JSONObject.parseObject(dataObject.get("data").toString());
            for (String s : paramArray) {
                if (!paramListObject.containsKey(s)) {
                    LogUtil.logInfo("data json对象中不包含字段:" + s);
                    CsvAction.getInstance().RecordLogicConclusion(line, false);
                    return false;
                }
            }
        } catch (JSONException e) {
            JSONArray dataArray = JSONArray.parseArray(dataObject.get("data").toString());
            JSONObject ListObject = JSONObject.parseObject(dataArray.get(0).toString());
            for (String s : paramArray) {
                if (!ListObject.containsKey(s)) {
                    LogUtil.logInfo("data json对象中不包含字段:" + s);
                    CsvAction.getInstance().RecordLogicConclusion(line, false);
                    return false;
                }
            }
        }
        CsvAction.getInstance().RecordLogicConclusion(line, true);
        return true;
    }
}
