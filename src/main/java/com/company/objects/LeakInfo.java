package com.company.objects;

import java.util.Date;

public class LeakInfo {

    // Имя плагина
    private String nameResource;
    // Автор
    private String author;
    // Версия ресурса
    private String version;
    // Описание
    private String description;
    // Скачивания
    private int downloads;
    // Просмотры
    private int views;
    // Первый релиз
    private Date firstRelease;
    // Последний релиз
    private Date lastUpdate;
    // Рейтинг
    private String rating;
    // Сайт с которого бралась информация
    private String siteUrl;
    // ID ресурса
    private String resourceID;


    public void toStringObject() {
        System.out.println("======================");
        System.out.println(getNameResource() + "~" + getAuthor()
                + "~"  + getVersion() + "~"  + getDescription()
                + "~"  + getDownloads() + "~" + getViews()
                + "~" + getFirstRelease() + "~" + getFirstRelease() + "~" + getLastUpdate() + "~" + getSiteUrl());

    }

    @Override
    public String toString() {
        return (getNameResource() + "~" + getAuthor()
                + "~"  + getVersion() + "~"  + getDescription()
                + "~"  + getDownloads() + "~" + getViews()
                 + "~" + getFirstRelease() + "~" + getLastUpdate() + "~" + getSiteUrl());
    }

    public void writeObject() {
        System.out.println("======================");
        System.out.println("Автор " + getAuthor());
        System.out.println("Имя ресурса " + getNameResource());
        System.out.println("Версия " + getVersion());
        System.out.println("Описание " + getDescription());
        System.out.println("Скачиваний " + getDownloads());
        System.out.println("Просмотры " + getViews());
        System.out.println("Первый релиз " + getFirstRelease());
        System.out.println("Последний релиз " + getLastUpdate());
        System.out.println("======================");

    }

    public String getNameResource() {
        return nameResource;
    }

    public void setNameResource(String nameResource) {
        this.nameResource = nameResource;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getFirstRelease() {
        return firstRelease;
    }

    public void setFirstRelease(Date firstRelease) {
        this.firstRelease = firstRelease;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }
}
