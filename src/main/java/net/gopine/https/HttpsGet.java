package net.gopine.https;

import net.gopine.util.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsGet {

    private String url;

    public HttpsGet(String URL) {
        this.url = URL;
    }

    public String sendGet() {

        try {
            HttpURLConnection httpClient =
                    (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "GopineClient/b1.0");

            int responseCode = httpClient.getResponseCode();
            Logger.CustomLogger.info("HttpsGet", "Sending GET request to URL: " + url);
            Logger.CustomLogger.info("HttpsGet", "GET request to " + url + " returned a response code of: " + responseCode);

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getInputStream()))) {

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();

            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}