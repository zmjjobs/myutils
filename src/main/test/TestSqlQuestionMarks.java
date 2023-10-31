/**
 * @Author : mjzhud
 * 功能：SQL问号和一堆参数的拼接
 * @create 2023/3/28 14:00
 */
public class TestSqlQuestionMarks {
    public static void main(String[] args) {
        String path = "1、、/src/main/java/com/iss/finance/loan/billaccept/contract/dao/AcceptContractBillDao.java";
        path = path.substring(path.indexOf("src/"));
        System.out.println(path);
    }

    /**
     *
     * 功能描述: 去掉括号里面的内容
     *
     * @param: [context]
     * @return: java.lang.String
     * @date: 2018/7/12 0012 11:18
     */
    private static String clearBracket(String context) {
//        String bracket = context.substring(context.indexOf("（"), context.indexOf("）") + 1);
//        context = context.replace(bracket, "");
//
//        context.substring(context.lastIndexOf())
//
//        return context;

        // 修改原来的逻辑，防止右括号出现在左括号前面的位置
        int head = context.indexOf('('); // 标记第一个使用左括号的位置
        if (head == -1) {
            return context; // 如果context中不存在括号，什么也不做，直接跑到函数底端返回初值str
        } else {
            int next = head + 1; // 从head+1起检查每个字符
            int count = 1; // 记录括号情况
            do {
                if (context.charAt(next) == '(') {
                    count++;
                } else if (context.charAt(next) == ')') {
                    count--;
                }
                next++; // 更新即将读取的下一个字符的位置
                if (count == 0) { // 已经找到匹配的括号
                    String temp = context.substring(head, next);
                    context = context.replace(temp, ""); // 用空内容替换，复制给context
                    head = context.indexOf('('); // 找寻下一个左括号
                    next = head + 1; // 标记下一个左括号后的字符位置
                    count = 1; // count的值还原成1
                }
            } while (head != -1); // 如果在该段落中找不到左括号了，就终止循环
        }
        return context; // 返回更新后的context
    }
}
