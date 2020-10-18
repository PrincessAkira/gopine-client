package net.gopine.https;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class HttpsPost {

    public String URI;

    public HttpsPost(String URI) {
        this.URI = URI;
    }

    public HttpClient httpClient = HttpClients.createDefault();
    public HttpPost httpPost = new HttpPost(URI);

    public List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    public void addParameter(BasicNameValuePair basicNameValuePair) {
        this.parameters.add(basicNameValuePair);
    }

    public HttpResponse response;
    public HttpEntity entity = response.getEntity();


    public HttpEntity getEntity() {
        try {
            response = httpClient.execute(httpPost);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

}