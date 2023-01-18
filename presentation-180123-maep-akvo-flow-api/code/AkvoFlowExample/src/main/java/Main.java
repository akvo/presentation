import DataHandler.Folder;
import Util.Regex;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {

    public static void main(@NotNull String[] args) throws URISyntaxException, IOException, JSONException {
        Regex url = new Regex(args[0]);
        url.check();
        Auth auth = new Auth();
        if (args.length == 0) {
            System.out.println("Require URL in the arguments");
            System.exit(1);
        }
        try {
            auth.login();
        } catch (DotenvException e) {
            System.out.println(".env file not found");
            System.exit(1);
        }
        String id_token = auth.getAuthEntity().id_token;
        RequestBuilder req = new RequestBuilder(id_token);
        JSONObject res = req.execute(args[0]);

        /*
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJsonTree(res));
         */

        if (Objects.equals(url.getEndpoint(), "folders")) {
            Folder folderData = new Folder(res);
            folderData.print();
        } else {
            System.out.println(res);
        }
    }
}