package com.zengzp.project.util;

import com.zengzp.project.entity.GoodsInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/30 17:14
 * @description：爬虫解析JD页面
 * @modified By：
 * @version: 1$
 */
@Component
public class ParseJDUtil {
    public List<GoodsInfo> parseHTML(String keyword) throws IOException {
        List<GoodsInfo> objects = new ArrayList<GoodsInfo>();
        String url="https://search.jd.com/Search?keyword="+keyword;
        Document document = Jsoup.parse(new URL(url), 300000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        GoodsInfo info=null;
            for (int i=0;i<elements.size();i++){
                int price_size=elements.get(i).getElementsByClass("p-price").size();
                int name_size=elements.get(i).getElementsByClass("p-name").size();
                int img_size=elements.get(i).getElementsByClass("p-img").size();
                if(price_size <=0 || name_size<=0 || img_size<=0){
                    continue;
                }
                String price=elements.get(i).getElementsByClass("p-price").get(0).getElementsByTag("i").get(0).text();
                String name=elements.get(i).getElementsByClass("p-name").get(0).text();
                String imgUrl=elements.get(i).getElementsByTag("img").get(0).attr("data-lazy-img");
                info=new GoodsInfo();
                info.setId(i+1);
                info.setImgUrl(imgUrl);
                info.setName(name);
                info.setPrice(new Double(Optional.ofNullable(price).orElse("0")));
                objects.add(info);
            }

        return objects;
    }

    public static void main(String[] args) throws IOException {
        new ParseJDUtil().parseHTML("java");
    }
}
