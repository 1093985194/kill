package com.oyzy.kill.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号吗校验
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern
            .compile("^1[3|4|5|7|8][0-9]{9}$");

    public static boolean isMobile(String mobile){
        if (StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
