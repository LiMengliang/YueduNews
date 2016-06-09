package com.redoc.yuedu.news.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by limen on 2016/4/30.
 */
public class AllNewsChannels {
    public static NewsChannel headLine = new NewsChannel("头条", "T1348647909107", "http://c.m.163.com/nc/article/headline/T1348647909107/%d-20.html", 1);
    public static NewsChannel entertainment = new NewsChannel("娱乐", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 2);
    public static NewsChannel sociaty =  new NewsChannel("社会", "T1349837698345", "http://c.m.163.com/nc/article/list/T1349837698345/%d-20.html", 3);
    public static NewsChannel finance = new NewsChannel("财经", "T1348648756099", "http://c.m.163.com/nc/article/list/T1348648756099/%d-20.html", 4);
    public static NewsChannel tech = new NewsChannel("科技", "T1348649580692", "http://c.m.163.com/nc/article/list/T1348649580692/%d-20.html", 5);
    public static NewsChannel automobile = new NewsChannel ("汽车", "T1348654060988", "http://c.m.163.com/nc/article/list/T1348654060988/%d-20.html", 6);
    public static NewsChannel cellphone =  new NewsChannel("手机", "T1348649654285", "http://c.m.163.com/nc/article/list/T1348649654285/%d-20.html", 7);
    public static NewsChannel emotion = new NewsChannel ("情感", "T1348650839000", "http://c.m.163.com/nc/article/list/T1348650839000/%d-20.html", 8);
    public static NewsChannel movie = new NewsChannel ("电影", "T1348648650048", "http://c.m.163.com/nc/article/list/T1348648650048/%d-20.html", 9);
    public static NewsChannel nba =  new NewsChannel("NBA", "T1348649145984", "http://c.m.163.com/nc/article/list/T1348649145984/%d-20.html", 10);
    public static NewsChannel digital =  new NewsChannel("数码", "T1348649776727", "http://c.m.163.com/nc/article/list/T1348649776727/%d-20.html", 11);
    public static NewsChannel mobile =  new NewsChannel("移动", "T1351233117091", "http://c.m.163.com/nc/article/list/T1351233117091/%d-20.html", 12);
    public static NewsChannel realestate = new NewsChannel ("房产", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 13);
    public static NewsChannel cba =  new NewsChannel("CBA", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 14);
    public static NewsChannel joke = new NewsChannel ("笑话", "T1350383429665", "http://c.m.163.com/nc/article/list/T1350383429665/%d-20.html", 15);
    public static NewsChannel fasion = new NewsChannel ("时尚", "T1348650593803", "http://c.m.163.com/nc/article/list/T1348650593803/%d-20.html", 16);
    public static NewsChannel beijin = new NewsChannel ("北京", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 17);
    public static NewsChannel war = new NewsChannel ("军事", "T1348648517839", "http://c.m.163.com/nc/article/list/T1348648517839/%d-20.html", 18);
    public static NewsChannel eGame = new NewsChannel ("游戏", "T1348654151579", "http://c.m.163.com/nc/article/list/T1348654151579/%d-20.html", 19);
    public static NewsChannel pickOut = new NewsChannel ("精选", "T1370583240249", "http://c.m.163.com/nc/article/list/T1370583240249/%d-20.html", 20);
   public static NewsChannel education =  new NewsChannel("教育", "T1348654225495", "http://c.m.163.com/nc/article/list/T1348654225495/%d-20.html", 21);
    public static NewsChannel bbs =  new NewsChannel("论坛", "T1349837670307", "http://c.m.163.com/nc/article/list/T1349837670307/%d-20.html", 22);
    public static NewsChannel tourism =  new NewsChannel("旅游", "T1348654204705", "http://c.m.163.com/nc/article/list/T1348654204705/%d-20.html", 23);

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
