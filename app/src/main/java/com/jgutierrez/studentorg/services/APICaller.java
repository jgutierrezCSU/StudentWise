package com.jgutierrez.studentorg.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICaller {
    public final static String BASE_API_URL = "http://google.com/";

    public static String sendRequest(String contentURL, String method, String contentType, String payload) {
        String result = null;
        try {
            URL url = new URL(String.format("%1$s%2$s", BASE_API_URL, contentURL));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000 /* milliseconds */);
            conn.setRequestMethod(method);

            conn.setDoInput(true);
            if(!method.equals("GET")) {
                conn.setRequestProperty("Content-Type", contentType);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(payload);
                wr.flush();
                wr.close();
            }

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            result = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendTestRequest(String fullUrl, String method, String contentType, String payload) {
        String result = null;
        try {
            URL url = new URL(fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000 /* milliseconds */);
            conn.setRequestMethod(method);

            conn.setDoInput(true);
            if(!method.equals("GET")) {
                conn.setRequestProperty("Content-Type", contentType);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(payload);
                wr.flush();
                wr.close();
            }

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            result = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
