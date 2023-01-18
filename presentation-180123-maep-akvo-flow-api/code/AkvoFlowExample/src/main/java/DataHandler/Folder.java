package DataHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.lang.model.element.Element;
import java.io.*;
import java.util.*;

public class Folder {
    protected JSONArray data;
    protected String filename;
    public Folder(JSONObject data, String filename, String objectName) {
       this.data = data.getJSONArray(objectName);
       this.filename = filename;
    }
    public void print() throws IOException {
        if (this.data.length() == 0) {
            System.out.println("No Folders Available");
            System.exit(0);
        }
        File file = new File(String.format("./%s.csv", this.filename));
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        JSONObject firstObject = (JSONObject)  this.data.get(0);
        Object[] headers = (Object[]) firstObject.keySet().toArray();
        for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
            bw.write((String) headers[i]);
            if (i != headers.length - 1) {
                bw.write(",");
            }
        }
        for (Object datum : this.data) {
            JSONObject ds = (JSONObject) datum;
            bw.newLine();
            for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
                bw.write((String) ds.get((String) headers[i]));
                if (i != headers.length - 1) {
                    bw.write(",");
                }
            }
        }
        bw.close();
    }
}
