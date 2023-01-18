package DataHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.lang.model.element.Element;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Folder {
    JSONArray data;
    public Folder(JSONObject data) {
        this.data = data.getJSONArray("folders");
    }
    public void print() throws IOException {
        File file = new File("./folder-list.csv");
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }
        if (this.data.length() == 0) {
            System.out.println("NO Folders Available");
            System.exit(0);
        }
        JSONObject firstObject = (JSONObject)  this.data.get(0);
        Object[] headers = (Object[]) firstObject.keySet().toArray();
        for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
            System.out.print(headers[i]);
            if (i != headers.length - 1) {
                System.out.print(",");
            }
        }
        for (Object datum : this.data) {
            JSONObject ds = (JSONObject) datum;
            System.out.println();
            for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
                System.out.print(ds.get((String) headers[i]));
                if (i != headers.length - 1) {
                    System.out.print(",");
                }
            }
        }
    }
}
