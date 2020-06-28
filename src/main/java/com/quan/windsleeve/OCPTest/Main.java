package com.quan.windsleeve.OCPTest;


import com.quan.windsleeve.OCPTest.reflect.HeroFactory;
import com.quan.windsleeve.OCPTest.reflect.Kill;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Main {
    /*
    public static void main(String[] args) throws Exception {
        while (true) {
            String heroName = Main.input();
            switch (heroName){
                case "Gailun":
                    Gailun gailun = new Gailun();
                    gailun.r();
                    break;
                case "Hanbing":
                    Hanbing hanbing = new Hanbing();
                    hanbing.r();
                    break;
                case "Jiaoyue":
                    Jiaoyue jiaoyue = new Jiaoyue();
                    jiaoyue.r();
                    break;
                default:
                    throw new Exception("用户输入异常");
            }
        }
    }
    */

    /*
    public static void main(String[] args) throws Exception {
        while (true) {
            String heroName = Main.input();
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
            kill.r();
        }
    }
    */


//    public static void main(String[] args) throws Exception {
//       String heroName = Main.input();
//        /**
//         * 使用工厂模式的目的：就是为了将对象实例化的功能分离到工厂方法中，使业务代码中，变化的和
//         * 不变化的代码进行分离，下面的HeroFactory是简单工厂的实现，如果当前工厂类不能够保证足够的
//         * 稳定性，那么其实当前类的代码也不是足够稳定的。
//         * 简单工厂与抽象工厂的区别在于：简单工厂是对实例化对象的抽象，它所能创建的所有对象，都需要
//         * implement同一个接口。抽象工厂是对具体工厂的抽象，所有的具体工厂都需要implement 顶级的一个
//         * 抽象工厂接口
//         */
//        Kill kill = HeroFactory.getHero(heroName);
//        kill.r();
//    }

    public static void main(String[] args) throws Exception {
        String heroName = Main.input();
        Kill kill = (Kill)HeroFactory.getHero(heroName);
        kill.r();
        /**
         * 这两句话并没有体现出IOC和DI的含义，仅仅体现了我们可以通过容器来获取对象
         */
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext();
        app.getBean("beanName");
    }

    public static String input() {
        Scanner scanner = new Scanner(System.in);
        String directive = scanner.next();
        return directive;
    }
}
