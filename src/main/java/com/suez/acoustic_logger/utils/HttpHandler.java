package com.suez.acoustic_logger.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {

    public static String getData(String baseUrl){
        URL url = null;
        String response = null;
        HttpURLConnection con = null;
        try {
            url = new URL(baseUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000 /* milliseconds */);
            con.setConnectTimeout(15000 /* milliseconds */);
            con.setRequestMethod("GET");
            con.connect();
            //read response
            if (con.getResponseCode() == 200){
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }
                response = content.toString();
                reader.close();
            }
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        System.out.println(response);
        return response;
    }
}
