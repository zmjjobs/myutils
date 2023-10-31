package com.mjzhud.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileUtils {
    private static final Log logger = LogFactory.getLog(MyFileUtils.class);
    public static List<String> readPathList(String filePath) throws Exception {
        File file = new File(filePath);
        String encoding = EncodingUtils.GetEncoding(file);
        logger.info("存放改动文件列表的文件的字符集编码="+encoding);
        List<String> pathList = FileUtils.readLines(file,encoding);
        if (pathList == null || pathList.size() <= 0) {
            throw new Exception("文件列表为空！");
        }

        return pathList;
    }

    /*public static List<String> delRepeat(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }*/

    public static List<String> getTxtPath(String workSpacePath) {
        List<String> txtPath = new ArrayList<>();
        File file = new File(workSpacePath);
        getTxtFile(file,txtPath);
        return txtPath;
    }

    public static void getTxtFile(File file,List<String> txtPath) {
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            File currF = array[i];
            if (currF.isFile()) {
                if (currF.getName().endsWith(".txt")) {
                    txtPath.add(currF.getPath().replace("\\","/"));
                    if (txtPath.size() == 10) return;
                }
            } else if (currF.isDirectory()) {
                String lowerCase = currF.getName().toLowerCase();
                if (!lowerCase.startsWith("ifinance")
                        && !lowerCase.startsWith("itreasury")
                        && !lowerCase.startsWith("ebank")
                        && !lowerCase.startsWith("web")
                        && !lowerCase.startsWith("."))
                getTxtFile(currF,txtPath);
            }
        }
    }

    /**
     * 读取文件内容，返回的多行通过空格拼接为一行
     * @param filePath
     * @return
     * @throws IOException
     */
    public static StringBuffer readFileContentJointSpaceOneLine(String filePath) throws IOException {
        StringBuffer result = new StringBuffer();
        File sqlFile = new File(filePath);
        String encoding = EncodingUtils.GetEncoding(sqlFile);
        FileInputStream in = FileUtils.openInputStream(sqlFile);
        InputStreamReader iReader = new InputStreamReader(in, encoding);
        BufferedReader bReader = new BufferedReader(iReader);
        for(String line = bReader.readLine(); line != null; line = bReader.readLine()) {
            result.append(line).append(" ");
        }
        return result;
    }
}
