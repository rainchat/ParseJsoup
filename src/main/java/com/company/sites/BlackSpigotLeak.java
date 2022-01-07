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
import java.util.ArrayList;

import java.util.List;

public class BlackSpigotLeak {

    public static List<LeakInfo> pageIncr() {
        List<LeakInfo> leakInfos = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            System.out.println("Просмотр страницы " + i + "/2");
            leakInfos.addAll(parseSite(i));
        }
        System.out.println("Записываем данные в фал");
        return leakInfos;
    }

    public static List<LeakInfo> parseSite(int page) {

        List<LeakInfo> leakInfos = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://blackspigot.com/downloads/?page=" + page)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LeakInfo leakInfo = new LeakInfo();

        Elements scriptElements = doc.select("a[href]");

        List<String> sites = new ArrayList<>();
        for (Element element :scriptElements ){
            if (!(element.cssSelector().contains("div.structItem-cell.structItem-cell--main > div.structItem-title > a")
                    && !element.cssSelector().contains("div.structItem-cell.structItem-cell--main > div.structItem-title > a.labelLink"))) continue;

            //System.out.println(element.attr("href").toString());
            sites.add("https://blackspigot.com" + element.attr("href"));
        }

        for (String site: sites) {
            leakInfos.add(getLeak(site));
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException ignored)
            {}
        }
        return leakInfos;
    }

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

        //System.out.println(scriptElements.size());

        String css = "";
        if (scriptElements.size() == 17) {
            css = "#XF > body > script:nth-child(22)";
        } else if (scriptElements.size() == 18) {
            css = "#XF > body > script:nth-child(23)";
        } else if (scriptElements.size() == 14) {
            css = "#XF > body > script:nth-child(21)";
        } else {
            css = "sdsdagsfdgd";
        }
        for (Element element :scriptElements ){

            if (!element.cssSelector().equalsIgnoreCase(css)) continue;
            //System.out.println(element.cssSelector());

            for (DataNode node : element.dataNodes()) {

                System.out.println(node.getWholeData());

                JsonObject jsonObject = new JsonParser().parse(node.getWholeData()).getAsJsonObject();

                leakInfo.setDescription(jsonObject.get("description").toString());
                leakInfo.setNameResource(jsonObject.get("name").toString());
                if (jsonObject.has("version")) {
                    leakInfo.setVersion(jsonObject.get("version").toString());
                }
                leakInfo.setFirstRelease(jsonObject.get("dateCreated").toString());
                leakInfo.setLastUpdate(jsonObject.get("dateModified").toString());
                leakInfo.setAuthor(jsonObject.get("author").getAsJsonObject().get("name").toString());
                leakInfo.setSiteUrl(connect);
                leakInfo.toStringObject();
            }
        }
        return leakInfo;
    }

}
