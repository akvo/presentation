import Api.Auth;
import Api.RequestBuilder;
import DataHandler.Folder;
import Util.Regex;
import io.github.cdimascio.dotenv.DotenvException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(@NotNull String[] args) throws IOException, JSONException {
        Regex url = new Regex(args[0]);
        url.check();
        Auth auth = new Auth();
        if (args.length < 2) {
            System.out.println("Require URL and filename in the arguments");
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
        req.setEndpoint(args[0]);
        JSONObject res = req.execute();

        if (res.has("nextPageUrl")) {
            req.getAll(res);
        }

        if (Objects.equals(url.getEndpoint(), "folders")) {
            Folder folderData = new Folder(res, args[1], "folders");
            folderData.print();
        } else if (Objects.equals(url.getEndpoint(), "surveys")) {
            Folder folderData = new Folder(res, args[1], "surveys");
            folderData.print();
        } else if (Objects.equals(url.isSurveyDefinition(), true)) {
            System.out.println("this is survey");
        } else if (Objects.equals(url.getEndpoint(), "data_points")) {
            System.out.println(req.collections.length());
        } else if (Objects.equals(url.getEndpoint(), "form_instances")) {
            System.out.println(req.collections.length());
        } else {
            System.out.println(res);
        }
    }
}