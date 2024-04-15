import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class RequestBuilder {
    protected String token;

    public RequestBuilder(String refreshToken) {
        this.token = refreshToken;
    }

    public JSONObject execute(String url) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/vnd.akvo.flow.v2+json")
                .addHeader("Authorization", String.format("Bearer %s", this.token)).build();
        Response response = client.newCall(request).execute();
        String res = Objects.<ResponseBody>requireNonNull(response.body()).string();
        return new JSONObject(res);
    }
}
