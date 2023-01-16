import java.io.IOException;
import java.net.URISyntaxException;

// import com.google.gson.GsonBuilder;
// import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, JSONException{
        Auth auth = new Auth();
        auth.login("deden@akvo.org",  "4LRdywjhCNNhUBWFwqe^vugV");
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