package com.lning.melireader.core.utils;

import java.util.UUID;

/**
 * Created by Ning on 2019/2/6.
 */

public class RandomUtils {

    private RandomUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static String getRandomStr(int size) {
        if (size <= 0 || size > 32) size = 16;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, size);
    }
}
