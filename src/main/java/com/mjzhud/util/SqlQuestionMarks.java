package com.mjzhud.util;

import com.mjzhud.exception.BusinessException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : mjzhud
 * @create 2023/4/19 10:37
 * utilNum=4
 * fileOutputPath=D:\MyOutputFile
 * sqlAndParamsFilePath=D:\MyOutputFile\123.txt
 */
public class SqlQuestionMarks {
    public static String fileOutputPath;//结果输出路径
    public static String sqlAndParamsFilePath;//sql和参数路径

    public static void main(String[] args) {
        for(int i = 0; i < args.length; i++) {
            String keyValue[] = args[i].split("=");
            if (keyValue.length != 2) continue;
            keyValue[0] = keyValue[0].trim();
            keyValue[1] = keyValue[1].trim();
            switch (keyValue[0]) {
                case "fileOutputPath":
                    fileOutputPath = keyValue[1];
                    break;
                case "sqlAndParamsFilePath":
                    sqlAndParamsFilePath = keyValue[1];
                    break;
            }
        }
        Scanner sc = new Scanner(System.in);
        // 获取键盘输入的字符串
        while(StringUtils.isBlank(fileOutputPath)) {
            System.out.println("不能直接获取到[结果输出路径],请输入：");
            fileOutputPath = sc.next();
            System.out.println("结果输出路径："+fileOutputPath+"\n");
        }
        while(StringUtils.isBlank(sqlAndParamsFilePath)) {
            System.out.println("不能直接获取到[sql和参数路径],请输入：");
            sqlAndParamsFilePath = sc.next();
            System.out.println("sql和参数路径："+sqlAndParamsFilePath+"\n");
        }
        StringBuffer sqlAndParamBuffer = null;
        try {
            sqlAndParamBuffer = MyFileUtils.readFileContentJointSpaceOneLine(sqlAndParamsFilePath);
        } catch (IOException e) {
            throw new BusinessException(sqlAndParamsFilePath+"获取文件信息异常");
        }
        Assert.isNotNull(sqlAndParamBuffer,sqlAndParamsFilePath+"文件未获取到内容");
        Assert.isTrue(sqlAndParamBuffer.indexOf(";") > 0,sqlAndParamsFilePath+"文件中SQL部分必须以;结尾");
        String sqlAndParam = sqlAndParamBuffer.toString();
        String[] sqlAndParamArr = sqlAndParam.split(";");
        String sql = sqlAndParamArr[0]+";";
        String params = sqlAndParamArr[1];
        //String params = "0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 5(String), 2023-11-30(String), 2023-11-30(String), 1(String), 2(String), 3(String), 4(String), 5(String), 6(String), 7(String), 8(String), 9(String), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 0(Long), 5(String), 2023-11-30(String), 2023-11-30(String), 1(String), 2(String), 3(String), 4(String), 5(String), 6(String), 7(String), 8(String), 9(String)";
        //String params = "0, 0, 0, 0, 0, 0, 0, 0, 5, 2023-11-30, 2023-11-30, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 5, 2023-11-30, 2023-11-30, 1, 2, 3, 4, 5, 6, 7, 8, 9";
        String[] paramArr = params.split(",");
        String[] sqlArr = sql.split("\\?");
        if (paramArr.length + 1 != sqlArr.length) {
            throw  new BusinessException("paramArr.length="+paramArr.length
                    +","+"sqlArr.length="+sqlArr.length+",前者只能比后者少一个");
        }
        StringBuffer newSQL= new StringBuffer();
        for (int i = 0;i < paramArr.length;i++) {
            String[] currPArr = paramArr[i].split("\\(");
            String zeroStr = currPArr[0].trim();
            newSQL.append("\n").append(sqlArr[i].trim()).append(" ");
            Assert.isTrue(currPArr.length < 3,"有特殊情况，请联系管理员");
            if (currPArr.length == 2) {
                if(currPArr[1].indexOf("String") >= 0) {
                    newSQL.append("'").append(zeroStr).append("'");
                } else {
                    newSQL.append(zeroStr);
                }
            } else {//1
                try {
                    Number number = NumberUtils.toNum(zeroStr);
                    newSQL.append(number);
                } catch (Exception e) {
                    newSQL.append("'").append(zeroStr).append("'");
                }
            }
        }
        newSQL.append(sqlArr[sqlArr.length-1]);
        //System.out.println(newSQL);

        fileOutputPath = fileOutputPath + "result_"+DateUtils.nowDateTime()+".sql";
        try {
            MyFileUtils.print2File(newSQL,fileOutputPath,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
