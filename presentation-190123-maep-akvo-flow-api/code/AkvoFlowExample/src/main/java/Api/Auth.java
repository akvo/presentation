package Api;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static okhttp3.RequestBody.create;

public class Auth {

    protected static String authUrl = "https://akvofoundation.eu.auth0.com/oauth/token";
    protected AuthEntity authEntity;
    public AuthEntity getAuthEntity() {
        return this.authEntity;
    }
    public void login() throws IOException {
        Dotenv dotenv = Dotenv.load();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String credentials = String.format("username=%s&password=%s&",dotenv.get("AUTH0_USER"), dotenv.get("AUTH0_PWD")) +
                "grant_type=password&" +
                "client_id=S6Pm0WF4LHONRPRKjepPXZoX1muXm1JS&" +
                "scope=offline_access";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = create(mediaType, credentials);
        Request request = new Request.Builder()
                .url(authUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            assert response.body() != null;
            this.authEntity = gson.fromJson(response.body().string(), AuthEntity.class);
        } else {
            System.out.println(response.code());
            System.exit(1);
        }
    }
}