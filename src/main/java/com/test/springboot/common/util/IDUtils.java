package com.test.springboot.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getOrderId(String phone) {
        String date = sdf.format(new Date());
        Random random = new Random();
        int id =random.nextInt(89999) + 10000;
        return date + phone + id;
    }

    public static String transationId() {
        String date = sdf.format(new Date());
        Random random = new Random();
        int id = random.nextInt(89999999) + 10000000;
        return date + id;
    }
}
