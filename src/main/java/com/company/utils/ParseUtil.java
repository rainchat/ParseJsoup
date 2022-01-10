package com.company.utils;

import com.company.objects.LeakInfo;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtil {

    public static Document getDocumend(String site) {
        Document doc = null;
        try {
            doc = Jsoup.connect(site)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static LeakInfo getLeakFromJson(LeakInfo leakInfo,JsonObject jsonObject, String site) {

        leakInfo.setDescription(jsonObject.get("description").toString());
        leakInfo.setNameResource(jsonObject.get("name").toString());
        if (jsonObject.has("version")) {
            leakInfo.setVersion(jsonObject.get("version").toString());
        }
        leakInfo.setAuthor(jsonObject.get("author").getAsJsonObject().get("name").toString());
        leakInfo.setSiteUrl(site);
        String[] split = site.split("\\.");
        String result = "";
        if (split.length == 2) {
            result = split[1];
        } else {
            result = split[2];
        }
        leakInfo.setResourceID("https://blackspigot.com/downloads/" + result.replaceAll("/", "") );

        String[] strings = jsonObject.get("dateCreated").toString().replaceAll("\"", "").split("T");
        String data1 = strings[0] + " " +strings[1].split("\\+")[0];
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = formatter6.parse(data1);
            leakInfo.setFirstRelease(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        strings = jsonObject.get("dateModified").toString().replaceAll("\"", "").split("T");
        data1 = strings[0] + " " +strings[1].split("\\+")[0];

        try {
            Date date1 = formatter6.parse(data1);
            leakInfo.setLastUpdate(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return leakInfo;
    }

}
