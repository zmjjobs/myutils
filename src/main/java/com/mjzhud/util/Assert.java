package com.mjzhud.util;

import com.mjzhud.exception.BusinessException;

public class Assert {

    public static void isNotNull(Object obj,String errMsg){
        if (obj == null) {
           throw new BusinessException(errMsg);
        }
    }

    public static void isTrue(boolean obj,String errMsg){
        if (!obj) {
            throw new BusinessException(errMsg);
        }
    }
}
