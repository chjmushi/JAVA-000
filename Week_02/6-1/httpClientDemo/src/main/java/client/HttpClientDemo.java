package client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * created by xiayiyang on 2020/10/26
 */
public class HttpClientDemo {

    public static void main(String[] args) {
        doGet("http://localhost:8801");
    }

    public static void doGet(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("doGet-responseCode:"+statusCode);
            HttpEntity responseEntity = response.getEntity();
            System.out.println("doGet-responseContext:" + EntityUtils.toString(responseEntity,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void doPost(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("doPost-responseCode:"+statusCode);
            HttpEntity responseEntity = response.getEntity();
            System.out.println("doPost-responseContext:" + EntityUtils.toString(responseEntity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
