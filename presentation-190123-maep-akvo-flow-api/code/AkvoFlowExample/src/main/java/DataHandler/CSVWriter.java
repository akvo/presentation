package DataHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class CSVWriter {
    protected JSONArray data;
    protected String filename;
    public CSVWriter(JSONObject data, String filename, String objectName) {
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
            System.out.println("Fichier créé :" + file.getName());
        } else {
            System.out.println("Le fichier existe déjà:" + file.getName());
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        JSONObject firstObject = (JSONObject)  this.data.get(0);
        Object[] headers = (Object[]) firstObject.keySet().toArray();
        for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
            bw.write(headers[i].toString());
            if (i != headers.length - 1) {
                bw.write(",");
            }
        }
        for (Object datum : this.data) {
            JSONObject ds = (JSONObject) datum;
            bw.newLine();
            for (int i = 0; i < Objects.requireNonNull(headers).length; i++) {
                bw.write(ds.get(headers[i].toString()).toString());
                if (i != headers.length - 1) {
                    bw.write(",");
                }
            }
        }
        bw.close();
    }
}
