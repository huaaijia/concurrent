package com.github.flashWebSocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by huaaijia on 16/8/17.
 */
public class test {
//    public static void main(String args[]){
//        Date date = new Date(1453128011592L);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.print(sdf.format(date));
//    }
    public static void main(String[] args) {
        List<String> a = null;
        List<Object> b = convert(a, Object.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convert(List<?> list, Class<T> c) {
        return (List<T>)list;
    }
}
