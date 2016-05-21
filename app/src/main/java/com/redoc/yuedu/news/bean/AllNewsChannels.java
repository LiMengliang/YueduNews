package com.redoc.yuedu.news.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class AllNewsChannels {
    public static NewsChannel headLine = new NewsChannel("头条", "T1348647909107", "http://c.m.163.com/nc/article/headline/T1348647909107/%d-20.html", 1, true);
    public static NewsChannel entertainment = new NewsChannel("娱乐", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 2, true);
    public static NewsChannel sociaty =  new NewsChannel("社会", "T1349837698345", "http://c.m.163.com/nc/article/list/T1349837698345/%d-20.html", 3, true);
    public static NewsChannel finance = new NewsChannel("财经", "T1348648756099", "http://c.m.163.com/nc/article/list/T1348648756099/%d-20.html", 4, true);
    public static NewsChannel tech = new NewsChannel("科技", "T1348649580692", "http://c.m.163.com/nc/article/list/T1348649580692/%d-20.html", 5, true);
    public static NewsChannel automobile = new NewsChannel ("汽车", "T1348654060988", "http://c.m.163.com/nc/article/list/T1348654060988/%d-20.html", 6, true);
    public static NewsChannel cellphone =  new NewsChannel("手机", "T1348649654285", "http://c.m.163.com/nc/article/list/T1348649654285/%d-20.html", 7, true);
    public static NewsChannel emotion = new NewsChannel ("情感", "T1348650839000", "http://c.m.163.com/nc/article/list/T1348650839000/%d-20.html", 8, true);
    public static NewsChannel movie = new NewsChannel ("电影", "T1348648650048", "http://c.m.163.com/nc/article/list/T1348648650048/%d-20.html", 9, true);
    public static NewsChannel nba =  new NewsChannel("NBA", "T1348649145984", "http://c.m.163.com/nc/article/list/T1348649145984/%d-20.html", 10, true);
    public static NewsChannel digital =  new NewsChannel("数码", "T1348649776727", "http://c.m.163.com/nc/article/list/T1348649776727/%d-20.html", 11, true);
    public static NewsChannel mobile =  new NewsChannel("移动", "T1351233117091", "http://c.m.163.com/nc/article/list/T1351233117091/%d-20.html", 12, true);
    public static NewsChannel realestate = new NewsChannel ("房产", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 13, true);
    public static NewsChannel cba =  new NewsChannel("CBA", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 14, true);
    public static NewsChannel joke = new NewsChannel ("笑话", "T1350383429665", "http://c.m.163.com/nc/article/list/T1350383429665/%d-20.html", 15, true);
    public static NewsChannel fasion = new NewsChannel ("时尚", "T1348650593803", "http://c.m.163.com/nc/article/list/T1348650593803/%d-20.html", 16, true);
    public static NewsChannel beijin = new NewsChannel ("北京", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 17, true);
    public static NewsChannel war = new NewsChannel ("军事", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 18, true);
    public static NewsChannel eGame = new NewsChannel ("游戏", "T1348654151579", "http://c.m.163.com/nc/article/list/T1348654151579/%d-20.html", 19, true);
    public static NewsChannel pickOut = new NewsChannel ("精选", "T1370583240249", "http://c.m.163.com/nc/article/list/T1370583240249/%d-20.html", 20, true);
    public static NewsChannel radio = new NewsChannel ("电台", "T1379038288239", "http://c.m.163.com/nc/article/list/T1379038288239/%d-20.html", 21, false);
    public static NewsChannel education =  new NewsChannel("教育", "T1348654225495", "http://c.m.163.com/nc/article/list/T1348654225495/%d-20.html", 22, true);
    public static NewsChannel bbs =  new NewsChannel("论坛", "T1349837670307", "http://c.m.163.com/nc/article/list/T1349837670307/%d-20.html", 23, true);
    public static NewsChannel tourism =  new NewsChannel("旅游", "T1348654204705", "http://c.m.163.com/nc/article/list/T1348654204705/%d-20.html", 24, true);

    private static List<NewsChannel> allChannels;
    public static List<NewsChannel> getAllChannels() {
        if(allChannels == null) {
            allChannels = new ArrayList<NewsChannel>()
            {
                {
                    add(headLine);
                    add(entertainment);
                    add(finance);
                    add(tech);
                    add(cba);
                    add(joke);
                    add(automobile);
                    add(fasion);
                    add(beijin);
                    add(war);
                    add(realestate);
                    add(eGame);
                    add(pickOut);
                    add(radio);
                    add(emotion);
                    add(movie);
                    add(nba);
                    add(digital);
                    add(mobile);
                    add(education);
                    add(bbs);
                    add(tourism);
                    add(cellphone);
                    add(sociaty);

                }
            };
        }
        Collections.sort(allChannels);
        return allChannels;
    }
}
