import Api.Auth;
import Api.RequestBuilder;
import DataHandler.CSVWriter;
import DataHandler.FormData;
import Util.Regex;
import io.github.cdimascio.dotenv.DotenvException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
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

        if (Objects.equals(url.getEndpoint(), "folders")) {
            CSVWriter csv = new CSVWriter(res, args[1], "folders");
            csv.print();
        } else if (Objects.equals(url.getEndpoint(), "surveys")) {
            CSVWriter csv = new CSVWriter(res, args[1], "surveys");
            csv.print();
        } else if (Objects.equals(url.isSurveyDefinition(), true)) {
            System.out.println("this is survey");
        } else if (Objects.equals(url.getEndpoint(), "data_points")) {
            System.out.println(req.collections.length());
        } else if (Objects.equals(url.getEndpoint(), "form_instances")) {
            String surveyId = url.getQueryParams().get("survey_id");
            String formId = url.getQueryParams().get("form_id");
            String surveyUrl = url.replaceUrl(String.format("surveys/%s", surveyId));
            req.setEndpoint(surveyUrl);
            JSONObject surveys = req.execute();
            req.getAll(res);
            JSONArray formInstances = req.collections;
            FormData formData = new FormData(formId, surveys, formInstances);
            formData.transform();
            JSONArray results = formData.getResults();
            System.out.println(results);
            JSONObject data = new JSONObject();
            data.put("form_instances", results);
            CSVWriter csv = new CSVWriter(data, args[1], "form_instances");
            csv.print();
        } else {
            System.out.println(res);
        }
    }
}