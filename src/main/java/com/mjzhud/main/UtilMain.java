package com.mjzhud.main;

import com.mjzhud.util.CopyFileByPathUtils;
import com.mjzhud.util.SqlAddTableUser;

/**
 * @Author : mjzhud
 * @create 2023/10/24 14:00
 */
public class UtilMain {
    public static void main(String[] args) {
        int utilNum = 0;
        if (args != null) {
            for(int i = 0; i < args.length; i++) {
                String keyValue[] = args[i].split("=");
                if (keyValue.length != 2) continue;
                keyValue[0] = keyValue[0].trim();
                if ("utilNum".equalsIgnoreCase(keyValue[0])) {
                    utilNum = Integer.parseInt(keyValue[1].trim());
                    break;
                }
            }
        }
        switch (utilNum) {
            case 0:
                CopyFileByPathUtils.main(args);//通过文件列表路径将文件copy出来
                break;
            case 1:
                SqlAddTableUser.main(args);//SQL因为场景原因需要把用户加上才能查询
                break;
        }
    }

}
