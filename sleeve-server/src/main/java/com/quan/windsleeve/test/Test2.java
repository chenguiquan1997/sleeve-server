package com.quan.windsleeve.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test2 {

    public static void main(String[] args) {
        ArrayList<Integer> arr1 = new ArrayList<>();
        arr1.add(1);
        arr1.add(2);
        arr1.add(3);
        arr1.add(4);
        arr1.add(5);
        ArrayList<Integer> arr2 = new ArrayList<>();
        arr2.add(1);
        arr2.add(2);
        arr2.add(3);
        List<Integer> notHave = Test2.findNotMatch(arr1,arr2);
//       notHave.forEach(n->{
//           System.out.println(n);
//       });
        System.out.println(notHave.toString());
    }

    public static List<Integer> findNotMatch(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        List<Integer> notHave = new ArrayList<>();

        for(int a1:arr1) {
            if(arr2.contains(a1)) {
                continue;
            }
            notHave.add(a1);
        }
       return notHave;
    }
}
