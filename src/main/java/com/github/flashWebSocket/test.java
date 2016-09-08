package com.github.flashWebSocket;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huaaijia on 16/8/17.
 */
public class test {
    public static void main(String args[]){
        Date date = new Date(1471337866260L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print(sdf.format(date));
    }
}
