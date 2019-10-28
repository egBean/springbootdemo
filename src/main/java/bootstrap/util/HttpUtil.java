package bootstrap.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static final int SUCCESS_CODE = 200;
    private static HttpClient httpClient;

    static {
        //超时时间20分钟
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1200000).setConnectionRequestTimeout(2000).setSocketTimeout(1200000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 发送post请求不带header
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String,String> params){
        return doPost(url,params,null);
    }

    /**
     * 发送post请求带header
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String doPost(String url, Map<String,String> params, Map<String, String> headers) {
        HttpPost httpPost ;
        HttpResponse response;
        String result = null;
        try {
            httpPost = new HttpPost(url);
            //设置请求头
            if(headers != null){
                Iterator headerIterator = headers.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, String> elem = (Map.Entry<String, String>) headerIterator.next();
                    httpPost.addHeader(elem.getKey(), elem.getValue());
                }
            }
            //设置参数
            List<NameValuePair> list = new ArrayList<>();
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf-8");
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = null;
            if (response != null) {
                entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送get请求
     * @param url 请求路径
     * @return
     */
    public static String httpGet(String url,Map<String,String> params){
        return httpGet(url,params,null);
    }

    public static String httpGet(String url,Map<String,String> params,Map<String,String> headers){
        HttpGet httpGet;
        HttpResponse resp;
        String result = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            if(params != null){
                Iterator iterator = params.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                    nameValuePairList.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
                }
            }
            uriBuilder.addParameters(nameValuePairList);
            httpGet = new HttpGet(uriBuilder.build());
            //设置请求头
            if(headers != null){
                Iterator headerIterator = headers.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, String> elem = (Map.Entry<String, String>) headerIterator.next();
                    httpGet.addHeader(elem.getKey(), elem.getValue());
                }
            }
            resp = httpClient.execute(httpGet);
            int statusCode = resp.getStatusLine().getStatusCode();
            HttpEntity entity = resp.getEntity();
            result = EntityUtils.toString(entity,"UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
