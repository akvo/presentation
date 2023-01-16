package org.akvoflowrest;

import okhttp3.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Token {
    public String getToken(String username, String password) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        StringBuilder credentials = new StringBuilder();
        credentials.append(String.format("username=%s&password=%s", username, password));
        credentials.append("grant_type=password&");
        credentials.append("client_id=S6Pm0WF4LHONRPRKjepPXZoX1muXm1JS&");
        credentials.append("scope=openid email");
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, String.valueOf(credentials));
        Request request = new Request.Builder()
                .url("https://akvofoundation.eu.auth0.com/oauth/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
