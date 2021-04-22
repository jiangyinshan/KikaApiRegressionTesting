package AssertInterface;


import okhttp3.Response;

import java.io.IOException;

public interface ResponseCheck {
    public boolean CheckResponseFormat(int line, Response response, String responseBodyStr) throws IOException;

    public boolean RecordContent(int line, int responseCode, int errorCode, String errorMsg, String data) throws IOException;
}
