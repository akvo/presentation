import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;
import java.net.URISyntaxException;

class AuthEntity {
    protected String id_token;
    protected String refresh_token;
    protected String access_token;

    public AuthEntity(String id_token, String access_token, String refresh_token) {
        this.id_token = id_token;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }
}

public class Auth {

    protected AuthEntity authEntity;
    private static String url = "https://akvofoundation.eu.auth0.com/oauth/token";

    public AuthEntity getAuthEntity() {
        return this.authEntity;
    }
    public void login(String username, String password) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        StringBuilder credentials = new StringBuilder();
        credentials.append(String.format("username=%s&password=%s&", username, password));
        credentials.append("grant_type=password&");
        credentials.append("client_id=S6Pm0WF4LHONRPRKjepPXZoX1muXm1JS&");
        credentials.append("scope=offline_access");
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, String.valueOf(credentials));
        Request request = new Request.Builder()
                .url(this.url)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        this.authEntity = gson.fromJson(response.body().string(), AuthEntity.class);
    }
}