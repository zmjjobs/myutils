package com.mjzhud.util;

import org.apache.commons.lang.StringUtils;


public class NumberUtils {
    public static int parseInt(String strInt,int defaultValue) {
    	if (StringUtils.isBlank(strInt)) return defaultValue;
    	try {
    		return Integer.parseInt(strInt);
    	}catch (Exception e) {
    	}
    	return defaultValue;
    }
    

}
