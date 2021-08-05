package AssertInterface;


import okhttp3.Response;

import java.io.IOException;

public interface ResponseCheck {
     boolean CheckResponseFormat(int line, Response response, String responseBodyStr) throws IOException;

     boolean RecordContent(int line, int responseCode, int errorCode, String errorMsg, String data) throws IOException;
}
