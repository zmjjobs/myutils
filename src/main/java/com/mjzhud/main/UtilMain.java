package com.mjzhud.main;

import com.mjzhud.constant.FunctionEnum;
import com.mjzhud.constant.InfoConstant;
import com.mjzhud.util.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : mjzhud
 * @create 2023/10/24 14:00
 */
public class UtilMain {
    public static void main(String[] args) {
        System.out.println("如有问题请联系"+ InfoConstant.MY_EMAIL +"\n");
        String functions = "本系统拥有以下功能：\n";
        FunctionEnum[] values = FunctionEnum.values();
        Map<Integer,String> functionMap = new HashMap<>();
        for (FunctionEnum f : values) {
            functionMap.put(f.getUtilNum(),f.getInfo());
            functions += f.getUtilNum() + "、" + f.getInfo() + "\n";
        }
        System.out.println(functions);


        int utilNum = 0;
        Assert.isNotNull(args,"必须至少传入一个参数");
        for(int i = 0; i < args.length; i++) {
            String keyValue[] = args[i].split("=");
            if (keyValue.length != 2) continue;
            keyValue[0] = keyValue[0].trim();
            if ("utilNum".equalsIgnoreCase(keyValue[0])) {
                utilNum = Integer.parseInt(keyValue[1].trim());
                break;
            }
        }
        Assert.isTrue(utilNum > 0,"utilNum必须有值且是大于0的整数！\n");
        System.out.println("当前选择的是:"+utilNum+"、" + functionMap.get(utilNum));
        switch (utilNum) {
            case InfoConstant.FUNCTION_ONE:
                CopyFileByPathUtils.main(args);//通过文件列表路径将文件copy出来
                break;
            case InfoConstant.FUNCTION_TWO:
                SqlAddTableUser.main(args);//SQL因为场景原因需要把用户加上才能查询
                break;
            case InfoConstant.FUNCTION_THREE:
                FindTwoFilesDuplicatePath.main(args);//找出两文件路径列表中重复的路径
                break;
            case InfoConstant.FUNCTION_FOUR:
                SqlQuestionMarks.main(args);//SQL拼接参数
                break;
        }
    }

}
