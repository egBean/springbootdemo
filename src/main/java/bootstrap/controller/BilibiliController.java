package bootstrap.controller;

import bootstrap.domain.Bilibili;
import bootstrap.mapper.BilibiliMapper;
import bootstrap.spider.Spider;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BilibiliController {
    final String BILIBILI_URL = "https://www.bilibili.com/video/av";
    final String BILIBILI_MSG = "https://api.bilibili.com/x/web-interface/archive/stat?aid=";
    @Autowired
    private BilibiliMapper mapper;

    @RequestMapping("/crowler")
    @ResponseBody
    public String crowler(String avid) throws Exception {
        Bilibili bilibili = new Bilibili();
        bilibili.setAvId(Integer.parseInt(avid));
        Document doc = Spider.getHtml(BILIBILI_URL + avid);
        Elements elements = doc.getElementsByClass("video-title");

        if(elements !=null && elements.size()>0){
            for(int j = 0 ;j<elements.size();j++){
                Element element = elements.get(j);
                String title = element.attr("title");
                bilibili.setTitle(element.text());//设置标题
            }
        }
        JSONObject json = Spider.getJson(BILIBILI_MSG + avid);
        JSONObject data = json.getJSONObject("data");
        SetPropertys(data,bilibili);
        mapper.insertSelective(bilibili);
        return "success";

    }

    private void SetPropertys(JSONObject data, Bilibili bilibili) {
        bilibili.setCoin(data.getInteger("coin"));
        bilibili.setDanmaku(data.getInteger("danmaku"));
        bilibili.setFavorite(data.getInteger("favorite"));
        bilibili.setLike(data.getInteger("like"));
        bilibili.setReply(data.getInteger("reply"));
        bilibili.setShare(data.getInteger("share"));
        bilibili.setView(data.getInteger("view"));

    }
}
