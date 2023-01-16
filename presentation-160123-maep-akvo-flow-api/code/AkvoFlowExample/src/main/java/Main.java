import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, JSONException{
        Dotenv dotenv = Dotenv.load();
        Auth auth = new Auth();
        auth.login(dotenv.get("AUTH0_USER"),  dotenv.get("AUTH0_PWD"));
        String id_token = auth.getAuthEntity().id_token;
        RequestBuilder req = new RequestBuilder(id_token);
        JSONObject res = req.execute("https://api-auth0.akvo.org/flow/orgs/maep/folders");
        /*
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJsonTree(res));
         */

        JSONArray folders = res.getJSONArray("folders");
        for (int i = 1; i < folders.length(); i++) {
            /*
            if (!Objects.equals(folders.getJSONObject(i).getString("name"), "TRAINING - AKVO FLOW API")) {
                continue;
            }
             */
            System.out.println(folders.getJSONObject(i));
        }
    }
}