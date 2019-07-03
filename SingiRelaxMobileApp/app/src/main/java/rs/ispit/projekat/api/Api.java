package rs.ispit.projekat.api;

import android.os.AsyncTask;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


@SuppressWarnings("CharsetObjectCanBeUsed")
public class Api {


    public static void getJSON (String url, final ReadDataHandler rdh){
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response = "";

                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String red;
                    while((red = br.readLine()) != null){
                        response += red + "\n";
                    }

                    //ovde je procitano sve
                    br.close();
                    con.disconnect();

                } catch (Exception e) {
                    //do greske ovde moze doci iz vise razloga
                    response ="[]";
                }

                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);//prazna poruka, cim je handler primi, znace da treba da uradi nesto sa tim
            }
        };

        task.execute(url);
    }

    public static void postDataJSON(String url, final JSONObject data, final ReadDataHandler rdh) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response = "";

                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
                    bw.write(data.toString());
                    bw.flush();
                    bw.close();
                    con.getOutputStream().close();

                    con.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String red;
                    while((red = br.readLine()) != null){
                        response += red + "\n";
                    }

                    //ovde je procitano sve
                    br.close();
                    con.disconnect();

                } catch (Exception e) {
                }
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);//prazna poruka, cim je handler primi, znace da treba da uradi nesto sa tim
            }
        };

        task.execute(url);
    }

    public static void putDataJSON(String url, final JSONObject data, final ReadDataHandler rdh) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String response = "";

                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("PUT");
                    con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
                    bw.write(data.toString());
                    bw.flush();
                    bw.close();
                    con.getOutputStream().close();

                    con.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String red;
                    while((red = br.readLine()) != null){
                        response += red + "\n";
                    }

                    //ovde je procitano sve
                    br.close();
                    con.disconnect();

                } catch (Exception e) {
                }
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);//prazna poruka, cim je handler primi, znace da treba da uradi nesto sa tim
            }
        };

        task.execute(url);
    }
}
