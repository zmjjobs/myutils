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

    public static Number toNum(String str) throws Exception {
		if (str.indexOf(".") >= 0) {//小数
			return Double.parseDouble(str);
		} else {
			return Long.parseLong(str);
		}
	}

	public static void main(String[] args) {
		String str = "1.23";
		Number number = null;
		try {
			number = toNum(str);
		} catch (Exception e) {
			System.out.println("是字符串");
			e.printStackTrace();
		}
		System.out.println(number);
	}
}
