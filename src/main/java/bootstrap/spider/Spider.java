package bootstrap.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class Spider {

    private static final Logger LOGGER = LoggerFactory.getLogger(Spider.class);

    final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";
    private static final HttpClientBuilder httpClientBuilder = HttpClients.custom();
    static{
        httpClientBuilder.setUserAgent(userAgent);
    }

    public static void main(String[] args) throws Exception {
        //https://api.bilibili.com/x/web-interface/archive/stat?aid=23497421 https://www.bilibili.com/video/av54861765

        Document doc = getHtml("https://www.bilibili.com/video/av54861765");
        Elements elements = doc.getElementsByClass("tit tr-fix");
        String title = null;//获取标题
        if(elements !=null && elements.size()>0){
            for(int j = 0 ;j<elements.size();j++){
                Element element = elements.get(j);
                title = element.text();
            }
        }


    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }


    public static Document getHtml(String url) throws Exception {
        CloseableHttpClient client = httpClientBuilder.build();
        HttpGet req = new HttpGet(url);
        CloseableHttpResponse resp = client.execute(req);
        byte[] buf = new byte[2048];
        InputStream is = resp.getEntity().getContent();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while( (i=is.read(buf)) != -1){
            sb.append(new String(buf,0,i));
        }
        Document doc = Jsoup.parse(sb.toString());
        return doc;
    }

    public static JSONObject getJson(String url) throws Exception {
        CloseableHttpClient client = httpClientBuilder.build();
        HttpGet req = new HttpGet(url);
        CloseableHttpResponse resp = client.execute(req);
        byte[] buf = new byte[2048];
        InputStream is = resp.getEntity().getContent();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while( (i=is.read(buf)) != -1){
            sb.append(new String(buf,0,i));
        }
        JSONObject obj = JSON.parseObject(sb.toString());
        return obj;
    }
}
