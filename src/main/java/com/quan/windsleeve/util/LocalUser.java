package com.quan.windsleeve.util;

import com.quan.windsleeve.model.User;

import java.util.HashMap;
import java.util.Map;

public class LocalUser {

    private static ThreadLocal<Map<String,Object>> threadLocalUsers = new ThreadLocal<>();

    public static void setUser(User user,Integer scope) {
        Map<String,Object> localUserMap = new HashMap<>();
        localUserMap.put("user",user);
        localUserMap.put("scope",scope);
        //调用set()，是将数据存储到当前线程中
        LocalUser.threadLocalUsers.set(localUserMap);
    }

    public static User getUser() {
        //调用get()，是从当前线程中取出本地数据
        Map<String,Object> userMap = LocalUser.threadLocalUsers.get();
        User user = (User) userMap.get("user");
        Integer scope = (Integer) userMap.get("scope");
        return user;
    }

    public static void clear() {
        LocalUser.threadLocalUsers.remove();
    }
}
