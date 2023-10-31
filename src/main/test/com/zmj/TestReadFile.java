package com.zmj;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author : mjzhud
 * @create 2023/8/21 21:00
 */
public class TestReadFile {
    public static void main(String[] args) throws IOException {
        //读取文本文件的所有行到一个集合
       List<String> lines= FileUtils.readLines(new File("C:\\Users\\mjzhud\\Desktop/123.txt"),"utf-8");
       String ss = "";
        for (String s : lines) {
            ss+=s.trim();
        }
        System.out.println(ss);
        //读取文件内容到一个字符串
        //String str = FileUtils.readFileToString(new File("C:\\Users\\mjzhud\\Desktop\\20230821网银境外回款日志/aaaa.txt"), "utf-8");
        //System.out.println(str);
        //读取文件到一个byte数组
        //FileUtils.readFileToByteArray(new File("D:/fileUtis/aa.txt"));
    }
}
