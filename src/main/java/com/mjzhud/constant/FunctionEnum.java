package com.mjzhud.constant;

public enum FunctionEnum {
    CopyFileByPathUtils(1,"用于抽取代码文件补丁、jar等"),
    SqlAddTableUser(2,"SQL因为场景原因需要把用户加上才能查询"),
    FindTwoFilesDuplicatePath(3,"找出两文件路径列表中重复的路径"),
    SqlQuestionMarks(4,"SQL拼接参数");


    private int utilNum;
    private String info;

    FunctionEnum(int utilNum,String info) {
        this.utilNum = utilNum;
        this.info = info;
    }

    public int getUtilNum() {
        return utilNum;
    }

    public String getInfo() {
        return info;
    }
}
