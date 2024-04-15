package DataHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.IntStream;

public class FormData {
    private final JSONArray formInstances;
    private JSONArray questionGroups;
    private final JSONArray results = new JSONArray();

    private final List<String> noHandler = new ArrayList<>(
            Arrays.asList("FREE TEXT", "NUMBER","BARCODE",
            "DATE","GEOSHAPE", "SCAN", "CADDISFLY"));
    private final List<String> fileHandler = new ArrayList<>(
            Arrays.asList("PHOTO", "VIDEO"));

    public JSONArray getResults() {
        return this.results;
    }

    private Object listHandler (JSONArray data, String target) {
        StringBuilder response = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            response.append(data.getJSONObject(i).getString(target));
            if (data.length() > 1 && i < data.length()) {
                response.append("|");
            }
        }
        return response.toString();
    }

    private Object geoHandler(JSONObject data) {
        return "%s|%s".formatted(data.get("lat").toString(), data.get("long").toString());
    }

    private Object valueHandler (Object data, String dataType) {
        if (noHandler.stream().anyMatch(s -> s.equals(dataType))) {
            return data;
        } else if (fileHandler.stream().anyMatch(s -> s.equals(dataType))) {
            return ((JSONObject) data).getString("filename");
        } else if (Objects.equals(dataType, "OPTION")) {
            return listHandler((JSONArray) data, "text");
        } else if (Objects.equals(dataType, "GEO")) {
            return geoHandler((JSONObject) data);
        } else if (Objects.equals(dataType, "CASCADE")) {
            return listHandler((JSONArray) data, "name");
        } else if (Objects.equals(dataType, "SIGNATURE")) {
            return ((JSONObject) data).getString("name");
        } else {
           return data;
        }
    }

    public FormData(String formId, @NotNull JSONObject forms, @NotNull JSONArray formInstances) {
        JSONArray formList = forms.getJSONArray("forms");
        for (int i = 0; i < formList.length(); i++) {
            if (Objects.equals(formList.getJSONObject(i).getString("id"), formId)) {
                this.questionGroups = formList.getJSONObject(i).getJSONArray("questionGroups");
            }
        }
        this.formInstances = formInstances;
    }
    public void transform() {
        for (int fi = 0; fi < this.formInstances.length(); fi++) {
            JSONObject formInstance = this.formInstances.getJSONObject(fi);
            JSONObject responses = formInstance.getJSONObject("responses");
            JSONObject transformed = new JSONObject();
            Object [] names = formInstance.keySet().toArray();
            for (Object name : names) {
                if (!Objects.equals(name, "responses")) {
                    transformed.put(name.toString(), formInstance.get((String) name));
                }
            }
            for (int qg = 0; qg < this.questionGroups.length(); qg++) {
                JSONObject questionGroup = this.questionGroups.getJSONObject(qg);
                String questionGroupId = questionGroup.getString("id");
                JSONArray questions = questionGroup.getJSONArray("questions");
                JSONArray answerGroups = responses.getJSONArray(questionGroupId);
                IntStream.range(0, answerGroups.length()).mapToObj(answerGroups::getJSONObject).forEach(
                        answerGroup -> IntStream.range(0, questions.length()).mapToObj(questions::getJSONObject).forEach(
                                question -> {
                    String questionId = question.getString("id");
                    String questionName = question.getString("name");
                    String questionType = question.getString("type");
                    if (answerGroup.has(questionId)) {
                        Object value = valueHandler(answerGroup.get(questionId), questionType);
                        transformed.put(questionName, value);
                    } else {
                        transformed.put(questionName, "");
                    }
                }));
            }
            this.results.put(transformed);
        }
    }
}
