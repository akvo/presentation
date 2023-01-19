package Api;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class RequestBuilder {
    protected String token;
    public String endpoint;
    
    public JSONArray collections = new JSONArray();
    public RequestBuilder(String refreshToken) {
       this.token = refreshToken;
    }
    public void setEndpoint(String url) {
        this.endpoint = url;
    }

    public JSONObject execute() throws IOException {
        System.out.println(String.format("GET: %s", this.endpoint));
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(this.endpoint)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/vnd.akvo.flow.v2+json")
                .addHeader("Authorization", String.format("Bearer %s", this.token)).build();
        String res;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Failed to get response");
                System.exit(1);
            }
            res = Objects.requireNonNull(response.body()).string();
        }
        return new JSONObject(res);
    }

    public void getAll(JSONObject data) throws IOException {
        Object[] keys;
        keys = Arrays.stream(data.keySet().toArray()).filter(x -> {
            return !Objects.equals(x, "nextPageUrl");
        }).toArray();
        String key = (String) keys[0];
        JSONArray currentData = (JSONArray) data.get(key);
        for(int i = 0; i < currentData.length(); i++) {
            this.collections.put(currentData.get(i));
        }
        if (!data.has("nextPageUrl")) {
            return;
        }
        this.setEndpoint((String) data.get("nextPageUrl"));
        JSONObject res = this.execute();
        JSONArray newData = res.getJSONArray(key);
        if (!Objects.equals(newData.length(), 0)) {
            getAll(res);
        }
    }
}
