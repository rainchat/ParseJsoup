package com.company.sites;

import com.company.Main;
import com.company.database.MySql;
import com.company.objects.LeakInfo;
import com.company.utils.FileUtil;
import com.company.utils.ParseUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BlackSpigotLeak {

    private static String siteName = "https://blackspigot.com";
    public static void parsePage() {

        int pages = 406;

        for (int i = 1; i <= pages; i++) {
            long databaseStartTime = System.currentTimeMillis();
            parseSite(siteName + "/downloads/?page=" + i);
            System.out.println("Просмотр страницы " + i + "/" + pages + " (" +  TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - databaseStartTime) + " сек)");
        }

    }

    public static void parseSite(String page) {

        Document doc = ParseUtil.getDocumend(page);

        Elements scriptElements = doc.select("a[href]");

        List<String> sites = new ArrayList<>();

        //======== Ищем ресурсы на сайте ================
        for (Element element :scriptElements.select("div.structItem-cell.structItem-cell--main > div.structItem-title > a") ){
            sites.add("https://blackspigot.com" + element.attr("href"));
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

        Document doc = ParseUtil.getDocumend(connect);

        LeakInfo leakInfo = new LeakInfo();

        Elements scriptElements = doc.getElementsByTag("script");

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

        for (Element element :scriptElements ){

            if (!element.cssSelector().equalsIgnoreCase(css)) continue;

            for (DataNode node : element.dataNodes()) {

                JsonObject jsonObject = new JsonParser().parse(node.getWholeData()).getAsJsonObject();
                ParseUtil.getLeakFromJson(leakInfo, jsonObject, connect);

            }
        }

        if (leakInfo.getSiteUrl() == null) return;
        MySql.addLeakInfo(leakInfo, Main.getParseApi().getMySql());
        FileUtil.write(leakInfo.toString());
    }




}
