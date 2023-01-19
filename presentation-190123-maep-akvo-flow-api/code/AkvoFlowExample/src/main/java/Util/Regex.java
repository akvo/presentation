package Util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    protected String url;
    protected String endpoint;

    protected String fullUrl;

    public Regex(String url) {
        this.url = url;
    }
    public String getEndpoint() {
        String[] array = this.endpoint.split("\\?",-1);
        return array[0];
    }
    public Map<String, String> getQueryParams() {
        final String regex = "(\\?|&)?([a-z_A-Z]+=+[0-9]+)";
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(this.url);
        HashMap<String, String> queryParams = new HashMap<>();
        while(matcher.find()) {
            String[] array = matcher.group(2).split("\\=", -1);
            queryParams.put(array[0], array[1]);
        }
        return queryParams;
    }

    public String replaceUrl(String endpoint) {
        final String regex = "^.*\\/";
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(this.url);
        matcher.find();
        return matcher.group(0) + endpoint;
    }

    public void check() {
        final String regex = "^(:?[^/]*/){6}(.*)$";
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(this.url);
        boolean b = matcher.find();
        assert b;
        this.endpoint = matcher.group(2);
    }

    public boolean isSurveyDefinition() {
       final String regex = "(surveys/).([0-9])*";
       final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
       final Matcher matcher = pattern.matcher(this.url);
       return matcher.find();
    }
}
