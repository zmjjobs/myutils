package com.mjzhud.util;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : mjzhud
 * @create 2023/10/24 15:30
 * @desc SQL因为场景原因需要把用户加上才能查询
 * utilNum=1 user=GFCLTEST20231016 filePath=C:/Users/mjzhud/Desktop/abc.sql
 */
public class SqlAddTableUser {
    public static final String select = "select ";//select
    public static final String fromLeftBracket = "from ( ";//from (
    public static final String from = "from ";//from
    public static final String where = "where ";//where
    public static final String group = "group";//group
    public static final String order = "order ";//order
    public static final String union = "union ";//union
    public static final String selectReplace = " S123.456 ";//select
    public static final String fromLeftBracketReplace = " A123.456 ";//from (
    public static final String fromNormalReplace = " M987.654 ";//from正常跟着的部分

    public static String filePath;
    public static String user;
    public static StringBuffer sqlBuffer;
    public static StringBuffer sumResultBuffer = new StringBuffer();
    public static void main(String[] args) {

        if (args != null) {
            for(int i = 0; i < args.length; i++) {
                String keyValue[] = args[i].split("=");
                if (keyValue.length != 2) continue;
                keyValue[0] = keyValue[0].trim();
                keyValue[1] = keyValue[1].trim();
                switch (keyValue[0]) {
                    case "user":
                        user = keyValue[1];
                        break;
                    case "filePath":
                        filePath = keyValue[1];
                        break;
                }
            }
        }
        Scanner sc = new Scanner(System.in);
        // 获取键盘输入的字符串
        while(StringUtils.isBlank(user)) {
            System.out.println("不能直接获取到[用户],请输入：");
            user = sc.next();
            System.out.println("用户："+user+"\n");
        }
        user += ".";
        while(StringUtils.isBlank(filePath)) {
            System.out.println("不能直接获取到[文件路径],请输入：");
            filePath = sc.next();
            System.out.println("文件路径："+filePath+"\n");
        }
        filePath = filePath.replace("\\","/");

        try {
            sqlBuffer = MyFileUtils.readFileContentJointSpaceOneLine(filePath);
            String sql = sqlBuffer.toString().toLowerCase(Locale.ROOT);
            sql = sql.replaceAll("\\s+", " ");

            String[] sqlArr = sql.split(";");

            for (int i = 0;i< sqlArr.length;i++) {
                sql = sqlArr[i];
                if (sql.contains("select") && sql.contains("from")) {
                    try {
                        sumResultBuffer.append(addSqlUser(sql)).append(";\n\n");
                    } catch (Exception e) {
                        sumResultBuffer.append(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            sumResultBuffer.append(e.getMessage());
            e.printStackTrace();
        }

        /*---------------------------------------------------------------------------------------------------------*/

        try {
            int fileIndex = filePath.lastIndexOf("/");
            int pointIndex = filePath.lastIndexOf(".");
            String fileParentPath = filePath.substring(0,fileIndex);
            String fileName = filePath.substring(fileIndex,pointIndex);
            String fileType = filePath.substring(pointIndex);
            filePath = fileParentPath + fileName+"_"+nowDateTime()+fileType;
            File logFile = new File(filePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            print2File(sumResultBuffer,logFile);
            //用记事本打开文件
            Process p = Runtime.getRuntime().exec( "notepad.exe " +filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuffer addSqlUser(String sql) throws Exception {
        Map<String,String> replaceMap = new HashMap<>();
        if (sql.contains("from(")) {
            sql = sql.replace("from(", fromLeftBracketReplace);
            replaceMap.put(fromLeftBracketReplace,fromLeftBracket);
        }
        if (sql.contains("from (")) {
            sql = sql.replace("from (", fromLeftBracketReplace);
            replaceMap.put(fromLeftBracketReplace,fromLeftBracket);
        }
        StringBuffer resultBuffer = new StringBuffer();
        for (int n = 0;n < 100;n++) {
            int fromIndex = sql.indexOf(from);

            resultBuffer.append(sql.substring(0,fromIndex));
            sql = sql.substring(fromIndex);

            int selectIndex = sql.indexOf(select);
            int whereIndex = sql.indexOf(where);
            int groupIndex = sql.indexOf(group);
            //int orderIndex = sql.indexOf(order);
            int unionIndex = sql.indexOf(union);

            int selectLeftBracketIndex = sql.indexOf(", (select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf(",(select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf(",( select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf(", ( select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf("join (select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf("join ( select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf("join( select");
            if (selectLeftBracketIndex < 0) selectLeftBracketIndex = sql.indexOf("join(select");
            int rightBracketIndex = sql.indexOf(")");

            int currIndex = 0;
            if (0 < whereIndex && whereIndex < unionIndex && unionIndex < selectIndex) {
                currIndex = whereIndex;
            } else if (0 <selectIndex && selectIndex < whereIndex && selectIndex < groupIndex
                    && selectLeftBracketIndex > 0 && selectLeftBracketIndex < selectIndex) {
                currIndex = selectLeftBracketIndex;
            } else if (0 < whereIndex && whereIndex < groupIndex && unionIndex < selectIndex) {
                currIndex = whereIndex;
            }  else if (0 < whereIndex && (rightBracketIndex > whereIndex || rightBracketIndex < 0)) {
                currIndex = whereIndex;
            }
            String fromFollow = null;
            if (currIndex == 0) {
                fromFollow = sql.substring(5);
            } else {
                fromFollow = sql.substring(5,currIndex-1);
            }
            String sqlnn = "";
            String[] sqlnArr = fromFollow.split(",");

            for (int i = 0;i < sqlnArr.length;i++) {
                sqlnn += user + sqlnArr[i].trim();
                if (i < sqlnArr.length-1) {
                    sqlnn +=",";
                }
            }
            sql = sql.replaceFirst(fromFollow.trim(),sqlnn);
            resultBuffer.append(sql.substring(0,sql.indexOf(sqlnn)+sqlnn.length()));
            sql = sql.substring(sql.indexOf(sqlnn)+sqlnn.length());
            if (sql.indexOf(from) < 0) break;
        }
        if (sql.contains("left join")) {
            int left_join = sql.indexOf("left join");
            String sqlnnn =  sql.substring(left_join,sql.length());
            int on = sqlnnn.indexOf(" on ");
            sqlnnn = sqlnnn.substring(9,on).trim();
            sql = sql.replace(sqlnnn,user+sqlnnn);
        }
        resultBuffer.append(sql);
        String result = resultBuffer.toString();
        if (replaceMap.size() > 0) {
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                result = result.replace(key,value);
            }
        }
        String[] resultArr = result.split(",");
        resultBuffer.setLength(0);
        for (int i = 0;i < resultArr.length;i++) {
            String r = resultArr[i].trim();
            if (r.contains("--")) {
                String[] sArr = r.split("\\s+");
                for (int j = 0;j < sArr.length;j++) {
                    String gg =  sArr[j];
                    resultBuffer.append(gg);
                    if (gg.contains("--")) {
                        resultBuffer.append("\n");
                    }
                    if (j < sArr.length - 1) {
                        resultBuffer.append(" ");
                    }
                }
            } else {
                if (r.contains(select)) {
                    r = r.replace(select,"\n"+select);
                }
                resultBuffer.append(r);
            }

            if (i < resultArr.length - 1) {
                resultBuffer.append(",");
                if (!resultArr[i+1].contains("--")) {
                    resultBuffer.append("\n");
                }
            }
        }
        return resultBuffer;
    }

    private static void print2File(StringBuffer buffer, File logFile) throws IOException {
        PrintStream ps = new PrintStream(logFile);
        System.setOut(ps);
        if (buffer != null && buffer.length() > 0) {
            System.out.print(buffer.toString());
        }
        ps.close();
    }

    private static String nowDateTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}

}
