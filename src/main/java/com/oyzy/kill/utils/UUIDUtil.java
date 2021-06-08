package com.oyzy.kill.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 生成UUID
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
