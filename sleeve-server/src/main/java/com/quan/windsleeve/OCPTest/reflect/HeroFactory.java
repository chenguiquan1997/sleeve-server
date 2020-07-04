package com.quan.windsleeve.OCPTest.reflect;

public class HeroFactory {
    public static Object getHero(String heroName) throws Exception {
        String path = "com.quan.windsleeve.OCPTest.reflect.";
        Class tClass = Class.forName(path + heroName);
        Object instance = tClass.newInstance();
        return instance;
    }
}
