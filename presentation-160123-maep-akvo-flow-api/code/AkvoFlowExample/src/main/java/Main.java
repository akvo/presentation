import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, JSONException{
        Dotenv dotenv = Dotenv.load();
        Auth auth = new Auth();
        auth.login(dotenv.get("AUTH0_USER"),  dotenv.get("AUTH0_PWD"));
        String id_token = auth.getAuthEntity().id_token;
        RequestBuilder req = new RequestBuilder(id_token);
        String res = req.execute("https://api-auth0.akvo.org/flow/orgs/uat1/folders");
        // Pretty Print
        JSONObject content = new JSONObject(res);
        /* Gson gson = new GsonBuilder().setPrettyPrinting().create();
        / System.out.println(gson.toJson(content));
         */
        JSONArray folders = content.getJSONArray("folders");
        for (int i = 1; i < folders.length(); i++) {
            System.out.println(folders.getJSONObject(i));
        }
    }
}