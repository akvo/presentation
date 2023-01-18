package Api;

public class AuthEntity {
    protected String access_token;
    public String id_token;
    protected String refresh_token;


    public AuthEntity(String id_token, String access_token, String refresh_token) {
        this.id_token = id_token;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }
}
