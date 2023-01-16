import okhttp3.*;

import java.io.IOException;

public class RequestBuilder {
    protected String token;

    public RequestBuilder(String refreshToken) {
        this.token = refreshToken;
    }

    public String execute(String url) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/vnd.akvo.flow.v2+json")
                .addHeader("Authorization", String.format("Bearer %s", this.token)).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
