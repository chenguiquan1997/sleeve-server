package com.quan.windsleeve.OCPTest.simpleFactory;

public class HeroFactory {
    public static Kill getHero(String heroName) throws Exception {
        Kill kill;
        switch (heroName){
            case "Gailun":
                kill = new Gailun();
                break;
            case "Hanbing":
                kill = new Hanbing();
                break;
            case "Jiaoyue":
                kill = new Jiaoyue();
                break;
            default:
                throw new Exception("用户输入异常");
        }
        return kill;
    }
}
