package AssertImpl;

import AssertInterface.ResourceArrayCheck;
import Util.CsvAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 响应数据中data json对象中为param json数组的接口业务验证类
 * data字段内容为数组类型的接口业务验证
 **/
public class ResourceArrayCheckImpl implements ResourceArrayCheck {
    public final String updateList = "updateList";
    public final String stickerList = "stickerList";
    public final String theme_list = "theme_list";
    public final String layout_list = "layout_list";
    public final String backgroundLlist = "backgroundList";
    public final String downloadLlist = "downloadList";
    public final String emoji_list = "emoji_list";
    public final String ab_test = "ab_test";
    public final String font_list = "font_list";
    public final String resource_list = "resource_list";
    public final String sound_list = "sound_list";
    public final String emoticon_list = "list";
    public final String launcher_list = "items";
    public final String bigEmoji_list = "data";
    public final String tinker_patch ="tinker_patch";
    public final String data_list = "data";
    public final String title_list = "titleList";
    public final String coolFont_list = "list";
    public final String thirdPartyAppsList = "apps";
    private volatile static ResourceArrayCheckImpl instance;

    public static ResourceArrayCheckImpl getInstance() {
        if (instance == null) {
            synchronized (ResourceArrayCheckImpl.class) {
                if (instance == null) {
                    instance = new ResourceArrayCheckImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public boolean Check(int line, String responseStr, String param) {
        JSONObject responseObject = JSON.parseObject(responseStr);
        JSONObject dataObject = JSONObject.parseObject(String.valueOf(responseObject));
        //捕获parase data数据空指针异常，即无data字段时记录业务逻辑结果为失败
        try {
            JSONObject paramListObject = JSONObject.parseObject(dataObject.get("data").toString());
            if (paramListObject.get(param) == null) {
                CsvAction.getInstance().RecordLogicConclusion(line, false);
            }
            JSONArray paramArray = JSONArray.parseArray(paramListObject.get(param).toString());
            if (paramArray == null || paramArray.size() < 1) {
                CsvAction.getInstance().RecordLogicConclusion(line, false);
            }
            CsvAction.getInstance().RecordLogicConclusion(line, true);
        } catch (NullPointerException e) {
            CsvAction.getInstance().RecordLogicConclusion(line, false);
            return false;
        }
        return true;
    }
}
