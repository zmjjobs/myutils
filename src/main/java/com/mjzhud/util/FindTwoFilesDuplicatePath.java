package com.mjzhud.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 找出两文件路径列表中重复的路径
 * 比如：用于svn代码冲突
 *  * utilNum=3
 *  * filePath=C:\Users\issuser\Desktop\fsdownload\liuzhanpeng.txt
 *  * filePath2=C:\Users\issuser\Desktop\fsdownload\qita.txt
 *  * fileOutputPath=C:\Users\issuser\Desktop\fsdownload\result.txt
 */
public class FindTwoFilesDuplicatePath {

    public static void main(String[] args) {
        //找两个文件的重复路径
        String aPath = null;
        String bPath = null;
        String fileOutputPath = null;
        for(int i = 0; i < args.length; i++) {
            String keyValue[] = args[i].split("=");
            if (keyValue.length != 2) continue;
            keyValue[0] = keyValue[0].trim();
            keyValue[1] = keyValue[1].trim();
            switch (keyValue[0]) {
                case "filePath":
                    aPath = keyValue[1].trim();
                    break;
                case "filePath2":
                    bPath = keyValue[1].trim();
                    break;
                case "fileOutputPath":
                    fileOutputPath = keyValue[1].trim();
                    break;
            }
        }
        List<String> aList = null;
        List<String> bList = null;
        try {
            aList = FileUtils.readLines(new File(aPath));
            bList = FileUtils.readLines(new File(bPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (aList != null && aList.size() > 0 && bList != null && bList.size() > 0) {
            Set<String> aSet = new HashSet<>();
            Set<String> bSet = new HashSet<>();
            aList.forEach(a ->{
                aSet.add(a.trim());
            });
            bList.forEach(b ->{
                bSet.add(b.trim());
            });
            StringBuffer sumBuffer = new StringBuffer();
            aSet.forEach(a->{
                bSet.forEach(b->{
                    if (a.equals(b)) {
                        sumBuffer.append(a+"\n");
                    }
                });
            });
            try {
                MyFileUtils.print2File(sumBuffer,fileOutputPath,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
