package com.quan.windsleeve.OCPTest.reflect;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component
public class Gailun implements Kill {

    public Gailun() {
        System.out.println("Gailun已经被实例化");
    }
    public void q() {
        System.out.println("Gailun-->Q");
    }
    public void w() {
        System.out.println("Gailun-->W");
    }
    public void e() {
        System.out.println("Gailun-->E");
    }
    public void r() {
        System.out.println("Gailun-->R");
    }
}
