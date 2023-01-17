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
        JSONObject res = req.execute("https://api-auth0.akvo.org/flow/orgs/maep/form_instances?survey_id=36540002&form_id=10740029");
        System.out.println(res);

        /*
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJsonTree(res));
         */


        // JSONArray folders = res.getJSONArray("folders");
        // for (int i = 0; i < folders.length(); i++) {
        //     if (!Objects.equals(folders.getJSONObject(i).getString("name"), "ATDA")) {
        //         continue;
        //     }
        //     String subfolder = folders.getJSONObject(i).getString("foldersUrl");
        //     JSONObject resSubFolder = req.execute(subfolder);
        //     JSONArray subFolders = resSubFolder.getJSONArray("folders");
        //     for (int j = 0; j < subFolders.length(); j++) {
        //         if (!Objects.equals(subFolders.getJSONObject(j).getString("name"), "ATDA_4")) {
        //             continue;
        //         }
        //         String surveys = subFolders.getJSONObject(j).getString("surveysUrl");
        //         JSONObject resSurveys = req.execute(surveys);
        //         JSONArray surveysList = resSurveys.getJSONArray("surveys");
        //         for (int k = 0; k < surveysList.length(); k++) {
        //             System.out.println(surveysList.getJSONObject(k));
        //         }
        //     }
        // }
    }
}