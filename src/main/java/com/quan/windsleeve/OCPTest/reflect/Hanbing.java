package com.quan.windsleeve.OCPTest.reflect;

import org.springframework.stereotype.Component;


public class Hanbing implements Kill {
    public Hanbing() {
        System.out.println("寒冰已被实例化");
    }
    public void q() {
        System.out.println("Hanbing-->Q");
    }
    public void w() {
        System.out.println("Hanbing-->W");
    }
    public void e() {
        System.out.println("Hanbing-->E");
    }
    public void r() {
        System.out.println("Hanbing-->R");
    }
}
