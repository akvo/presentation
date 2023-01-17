import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static okhttp3.RequestBody.create;

class AuthEntity {
    protected String access_token;
    protected String id_token;
    protected String refresh_token;


    public AuthEntity(String id_token, String access_token, String refresh_token) {
        this.id_token = id_token;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }
}

public class Auth {

    protected AuthEntity authEntity;
    public AuthEntity getAuthEntity() {
        return this.authEntity;
    }
    public void login() throws URISyntaxException, IOException {
        Dotenv dotenv = Dotenv.load();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String credentials = String.format("username=%s&password=%s&",dotenv.get("AUTH0_USER"), dotenv.get("AUTH0_PWD")) +
                "grant_type=password&" +
                "client_id=S6Pm0WF4LHONRPRKjepPXZoX1muXm1JS&" +
                "scope=offline_access";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = create(mediaType, String.valueOf(credentials));
        String url = "https://akvofoundation.eu.auth0.com/oauth/token";
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        assert response.body() != null;
        this.authEntity = gson.fromJson(response.body().string(), AuthEntity.class);
    }
}