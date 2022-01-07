package com.company.sites;

import com.company.objects.LeakInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DirectLeak {

    public static LeakInfo getLeak(String connect) {

        Document doc = null;
        try {
            doc = Jsoup.connect(connect)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LeakInfo leakInfo = new LeakInfo();

        Elements scriptElements = doc.getElementsByTag("script");

        for (Element element :scriptElements ){
            if (!element.cssSelector().equalsIgnoreCase("#XF > body > script:nth-child(22)")) continue;
            for (DataNode node : element.dataNodes()) {

                JsonObject jsonObject = new JsonParser().parse(node.getWholeData()).getAsJsonObject();

                leakInfo.setDescription(jsonObject.get("description").toString());
                leakInfo.setNameResource(jsonObject.get("name").toString());
                leakInfo.setVersion(jsonObject.get("version").toString());
                //leakInfo.setFirstRelease(new Date(jsonObject.get("dateCreated").toString()));
                //leakInfo.setLastUpdate(new Date(jsonObject.get("dateModified").toString()));
                leakInfo.setAuthor(jsonObject.get("author").toString());

                leakInfo.writeObject();
            }
        }
        return leakInfo;
    }
}
