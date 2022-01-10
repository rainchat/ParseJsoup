package com.company.sites;

import com.company.database.MySql;
import com.company.objects.LeakInfo;
import com.company.utils.ParseUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DirectLeak {

    public static MySql mySql;


    public static void pageIncr() {

        int pages = 3;

        for (int i = 1; i <= pages; i++) {
            long databaseStartTime = System.currentTimeMillis();
            parseSite(i);
            System.out.println("Просмотр страницы " + i + "/" + pages + " (" +  TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - databaseStartTime) + " сек)");
        }

    }

    public static void parseSite(int page) {

        Document doc = ParseUtil.getDocumend("https://directleaks.to/downloads/?page=" + page);

        Elements scriptElements = doc.select("a[href]");

        List<String> sites = new ArrayList<>();

        //======== Ищем ресурсы на сайте ================
        for (Element element : doc.select("div.structItem-cell.structItem-cell--main > div.structItem-title").select("a:nth-child(2)")){
            sites.add("https://directleaks.to" + element.attr("href"));
        }
        //==============================================

        for (String site: sites) {
            getLeak(site);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ignored)
            {}
        }
    }

    public static void getLeak(String connect) {


        Document doc = null;
        try {
            doc = Jsoup.connect(connect)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0")
                    .referrer("http://www.yandex.ru")
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LeakInfo leakInfo = new LeakInfo();

        Elements scriptElements = doc.getElementsByTag("script");

        String css = "";
        if (scriptElements.size() == 28) {
            css = "#XF > body > script:nth-child(30)";
        } else if (scriptElements.size() == 29) {
            css = "#XF > body > script:nth-child(23)";
        } else if (scriptElements.size() == 14) {
            css = "#XF > body > script:nth-child(21)";
        } else {
            css = "sdsdagsfdgd";
        }




        for (Element element : doc.select("dl.pairs.pairs--justified:nth-child(2)").select("dd") ){
            if (element.ownText().equals("")) continue;
            leakInfo.setDownloads(Integer.parseInt(element.ownText().replaceAll(",", "")));
        }
        for (Element element : doc.select("dl.pairs.pairs--justified:nth-child(3)").select("dd") ){
            if (element.ownText().equals("")) continue;
            leakInfo.setViews(Integer.parseInt(element.ownText().replaceAll(",", "")));
        }
        for (Element element : doc.select("dl.pairs.pairs--justified:nth-child(6)").select("span.u-srOnly")) {
            if (element.ownText().equals("")) continue;
            leakInfo.setRating(element.ownText());
        }

        //System.out.println(scriptElements.size());
        for (Element element : scriptElements){
            try {
                System.out.println(element.cssSelector() + " "  + scriptElements.size());
                System.out.println(element.data());
            } catch (Selector.SelectorParseException e) {
                e.printStackTrace();
            }
            //if (!(element.cssSelector().contains("#XF > body > div.p-body-main > div.p-body-content > div.p-body-pageContent > div.block:nth-child(3) > div.block-container > div.block-body.lbContainer.js-resourceBody > div.resourceBody > div.resourceBody-sidebar > div.resourceSidebarGroup:nth-child(1) > dl.pairs.pairs--justified:nth-child(3) > dd"))) continue;


        }
    }
}
