package net.gopine.https;

import net.gopine.util.Logger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class HttpsPost {

    public String URL;

    public HttpsPost(String URL) {
        this.URL = URL;
    }

    public URL url(String url) {
        try {
            return new URL(url);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private URLConnection connection(URL url) {
        try {
            return url(URL).openConnection();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpsPost(String URL, String JSONContent, Map<String, String> params) {
        this.URL = URL;
        try {
            HttpURLConnection http = (HttpURLConnection)connection(url(URL));
            assert http != null;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String, String> entry : params.entrySet()) {
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            byte[] out = JSONContent.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            Logger.info("POST Request completed!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}