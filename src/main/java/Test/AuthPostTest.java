package Test;
import okhttp3.*;

import java.io.*;

/**
 * 输出mojitok 的免费sticker pack所在位置
 **/
public class AuthPostTest {

    public final static String stickerSearchApi_dev = "https://api-dev.kikakeyboard.com/v1/open/stickers/search";
    public final static String stickerSearchApi_release = "https://api.kika.kikakeyboard.com/v1/outUser/like";
    public final static String PNG = "png";
    public final static String GIF = "gif";
    public final static int pngStickerPack = 0;
    public final static int gifStickerPack = 1;

    public static Request getSticekrBean(String url) {
        HttpUrl httpUrl = HttpUrl.parse(url)
                .newBuilder()
                .addQueryParameter("sign", "6b8b2d4789dc7f4ced3c4efcc0e8015a")
                .build();
        Request request = new Request.Builder()
                .url(httpUrl.toString())
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),"{\n" +
                        "\t\"theme_list\": [{\n" +
                        "\t\t\"key\": \"AjdS2-VAk\",\n" +
                        "\t\t\"likedTime\": 1620989649611,\n" +
                        "\t\t\"pkg_name\": \"com.ikeyboard.theme.chat.messnger\",\n" +
                        "\t\t\"preview\": \"https://kika-test-cdn.s3-us-wst-2.amazonaws.com/terminal/test/compress/20200601/f01cf1a4-a3f4-11ea-b4b3-061493b0cc7d.jpg.webp\",\n" +
                        "\t\t\"vip\": 0\n" +
                        "\t}]\n" +
                        "}"))
                .addHeader("User-Agent", "kika.emoji.keyboard.teclados.clavier/6543 (4067291a06754605b9389622403a4a5b/73750b399064a5eb43afc338cd5cad25) Country/US Language/en System/android Version/23 Screen/320")
                .addHeader("Connection", "keep-alive")
                .addHeader("content-type","application/json")
                .addHeader("authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuM0hOZ2JQa203Z0lBTWNjVjV2T3JqMjRRQ2gyIiwiaWF0IjoxNjIwOTg5NjI5LCJleHAiOjE2NTI1MjU2Mjl9.GNykf9--LL7hbhbwPUiw8UVH1TclO-ORoaZ8EZG7qK4")
                .build();
        return request;
    }


    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(getSticekrBean(stickerSearchApi_release)).execute();
        System.out.println(response.body().string());
    }
}
