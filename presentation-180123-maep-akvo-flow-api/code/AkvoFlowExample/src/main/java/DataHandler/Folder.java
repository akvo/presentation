package DataHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.lang.model.element.Element;
import java.io.*;
import java.util.*;

public class Folder {
    JSONArray data;
    public Folder(JSONObject data) {
        this.data = data.getJSONArray("folders");
    }
    public void print() throws IOException {
        if (this.data.length() == 0) {
            System.out.println("NO Folders Available");
            System.exit(0);
        }
        File file = new File("./output.csv");
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
            // System.out.print(headers[i]);
            bw.write((String) headers[i]);
            if (i != headers.length - 1) {
                bw.write(",");
                // System.out.print(",");
            }
        }
        for (Object datum : this.data) {
            JSONObject ds = (JSONObject) datum;
            bw.newLine();
            // System.out.println();
            for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
                // System.out.print(ds.get((String) headers[i]));
                bw.write((String) ds.get((String) headers[i]));
                if (i != headers.length - 1) {
                    // System.out.print(",");
                    bw.write(",");
                }
            }
        }
        bw.close();
    }
}
