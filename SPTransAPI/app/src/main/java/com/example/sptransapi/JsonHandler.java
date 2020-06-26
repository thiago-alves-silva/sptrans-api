package com.example.sptransapi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHandler {
    public static String getJson(String urlText, String method, String cookie) {
        String jsonText = "";
        try {
            URL url = new URL(urlText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            connection.setRequestProperty("Accept-Language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Cookie", "_ga=GA1.3.1870216860.1592920021; _gid=GA1.3.1325020647.1592920021; " + cookie);

            if(method.equals("POST")) {
                cookie = connection.getHeaderFields().toString();
                cookie = cookie.substring(cookie.indexOf("apiCredentials"), cookie.indexOf(" path"));
                return  cookie;
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);
            String line = "";
            while((line = bufferedReader.readLine()) != null) jsonText += line;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("GenericException", e.toString());
        }
        return jsonText;
    }
}
