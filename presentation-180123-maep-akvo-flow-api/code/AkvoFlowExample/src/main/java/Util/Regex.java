package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    protected String url;
    protected String endpoint;

    public Regex(String url) {
        this.url = url;
    }

    public String getEndpoint() {
        String[] array = this.endpoint.split("\\?",-1);
        return array[0];
    }
    public void check() {
        final String regex = "^(:?[^/]*/){6}(.*)$";
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(this.url);
        matcher.find();
        this.endpoint = matcher.group(2);
    }
}
