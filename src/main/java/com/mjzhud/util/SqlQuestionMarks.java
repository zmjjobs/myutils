package com.mjzhud.util;

/**
 * @Author : mjzhud
 * @create 2023/4/19 10:37
 */
public class SqlQuestionMarks {
    public static void main(String[] args) {

        //IDEA支持跟netpad++类似的功能，Alt键
        String sql ="select store.id, store.SETTLE_DATE, store.DRAFT_CODE , store.DRAFT_RANGE , info_ass.DRAFT_STATUS , store.SETTLE_DRAFT_AMOUNT, nvl(store.DRAFT_AMOUNT,0.00) - nvl(store.SETTLE_DRAFT_AMOUNT,0.00) no_settle_draft_amount, info_ass.DRAFT_TRANS_STATUS , store.DRAFT_AMOUNT , info.DUE_DATE , info.DRAWER_CLIENT_NAME DRAWER_NAME, info.ACCEPTOR_CLIENT_NAME ACCEPTOR_NAME, store.BEARER_DRAFT_ACC_NAME BEARER_NAME, store.BEARER_BIG_BANK_NAME BEARER_BIG_BANK, store.STOCKIN_DATE STOCK_IN_DATE, store.STOCKTO_DATE STOCK_OUT_DATE, store.FOREHAND_NAME , store.CURRENT_BUSINESS_TYPE STOCK_IN_TYPE, route.HANDLE_CHANNEL_CODE, route.HANDLE_CHANNEL_NAME , info_ass.DRAFT_CIRCULATION_STATUS , info_ass.DRAFT_RISK_STATUS , info.DRAW_DATE , info.DRAWER_ACCOUNT_CODE , info.DRAWER_BANK_CODE , info.DRAWER_BANK_NAME , info.DRAWER_BIG_BANK_NAME , info.ACCEPTOR_ACCOUNT_CODE , info.ACCEPTOR_BANK_NAME , info.ACCEPTOR_BIG_BANK_NAME , info.PAYEE_CLIENT_NAME PAYEE_NAME, info.PAYEE_ACCOUNT_CODE , info.PAYEE_BANK_CODE , info.PAYEE_BANK_NAME, info.PAYEE_BIG_BANK_NAME, store.BEARER_DRAFT_ACC_CODE BEARER_ACCOUNT_CODE, store.BEARER_DRAFT_BANK_CODE BEARER_BANK_CODE, store.BEARER_DRAFT_BANK_NAME BEARER_BANK_NAME, store.BEARER_BIG_BANK_NAME, info.DRAFT_CATEGORY , info.DRAFT_TYPE , store.DIRECT_TYPE , store.input_time INPUT_TIME, INFO.ACCEPT_DATE, INFO.DRAWER_BANK_ORG_CODE DRAWER_ORGAN_CODE, INFO.DRAWER_HANDLE_CHANNEL_CODE DRAWER_CHANNEL_CODE, INFO.DRAWER_COR_SOC_CODE DRAWER_SOC_CODE, INFO.DRAWER_CREDIT_LEVEL, INFO.DRAWER_CREDIT_SUBJECT DRAWER_CREDIT_EVALUATION_INS, INFO.DRAWER_CREDIT_DUE_DATE, INFO.PAYEE_BANK_ORG_CODE PAYEE_ORGAN_CODE, INFO.PAYEE_HANDLE_CHANNEL_CODE PAYEE_HANNLE_CODE, INFO.ACCEPTOR_CLIENT_CODE ACCEPT_CODE, INFO.ACCEPTOR_BANK_NAME ACCEPT_BANK_NAME, INFO.ACCEPTOR_BANK_CODE ACCEPT_BANK_CODE, INFO.ACCEPTOR_BANK_ORG_CODE ACCEPT_ORGAN_CODE, INFO.ACCEPTOR_HANDLE_CHANNEL_CODE ACCEPT_CHANNEL_CODE, INFO.DRAWER_GUARANTOR_NAME DRAW_GUARANTOR_NAME, INFO.DRAWER_GUARANTOR_ACC_CODE DRAW_GUARANTOR_ACCOUNT_CODE, INFO.DRAWER_GUARANTOR_BANK_CODE DRAW_GUARANTOR_BANK_CODE, INFO.DRAWER_GUARANTOR_BANK_NAME DRAW_GUARANTOR_BANK_NAME, INFO.ACCEPTOR_GUARANTOR_NAME ACCEPT_GUARANTOR_NAME, INFO.ACCEPTOR_GUARANTOR_ACC_CODE ACCEPT_GUARANTOR_ACCOUNT_CODE, INFO.ACCEPTOR_GUARANTOR_BANK_CODE ACCEPT_GUARANTOR_BANK_CODE, INFO.ACCEPTOR_GUARANTOR_BANK_NAME ACCEPT_GUARANTOR_BANK_NAME, INFO.CONTRACT_CODE from idms_d_current_store store left join idms_d_draft_info info on store.DRAFT_CODE = info.DRAFT_CODE left join idms_d_draft_info_ass info_ass on store.DRAFT_CODE = info_ass.DRAFT_CODE and store.DRAFT_RANGE = info_ass.DRAFT_RANGE left join idms_channel_route route on route.handle_channel_code = info.DRAWER_HANDLE_CHANNEL_CODE WHERE store.pay_or_receive = '02' and store.draft_status in ( ? , ? , ? , ? , ? ) and (store.OBLIGEE_CLIENT_CODE in ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ) ) and (store.SETTLE_DATE = ? or store.SETTLE_DATE is null) and info.DUE_DATE >= ? and info.DUE_DATE <= ? order by store.draft_code";
        sql = "";
        sql+="SELECT B.ID,                                                                              ";
        sql+="                               BT.CONTRACTCODE,                                           ";
        sql+="                               BT.TRANSACTIONTYPE,                                        ";
        sql+="                               B.BILLCODE CODE,                                           ";
        sql+="                               B.DRAFTRANGE,                                              ";
        sql+="                               B.BILLAMOUNT AMOUNT,                                       ";
        sql+="                               D.SETTLEDATE SETTLEDATE,                                   ";
        sql+="                               B.ENDDATE BILLDUEDATE,                                     ";
        sql+="                               BT.EXPIRYSETTDATE billBackDate,                            ";
        sql+="                               DECODE(BT.TRANSACTIONTYPE,                                 ";
        sql+="                                      101,                                                ";
        sql+="                                      B.ENDDATE,                                          ";
        sql+="                                      BT.EXPIRYSETTDATE) BILLENDDATE,                     ";
        sql+="                               NVL(B.INITIALINTEREST, 0.00) TOTALINTEREST,                ";
        sql+="                               NVL(BT.RATE, 0.0000) RATE,                                 ";
        sql+="                               NVL(A.PREDRAWINTEREST, 0) PREDRAWINTEREST,                 ";
        sql+="                               A.PREDRAWDATE,                                             ";
        sql+="                               NVL(B.HOLIDAYDAYS, 0) ADDDAYS,                             ";
        sql+="                               B.COLUUID                                                  ";
        sql+="                          FROM BILLTRAD_BILLINFO B                                        ";
        sql+="                          LEFT JOIN BILLTRAD_DELIVERYSHEET D                              ";
        sql+="                            ON B.CREDENCEID = D.ID                                        ";
        sql+="                          LEFT JOIN BILLTRAD_TRANSACTIONSHEET BT                          ";
        sql+="                            ON D.CONTRACTID = BT.ID                                       ";
        sql+="                         INNER JOIN V_P_USER_OFFICE O                                     ";
        sql+="                            ON O.OFFICEID = B.OFFICEID                                    ";
        sql+="                           AND O.USERID = ?                                               ";
        sql+="                         INNER JOIN V_P_USER_CURRENCY U                                   ";
        sql+="                            ON U.OFFICEID = B.OFFICEID                                    ";
        sql+="                           AND U.CURRENCYID = B.CURRENCYID                                ";
        sql+="                           AND U.USERID = ?                                               ";
        sql+="                          LEFT OUTER JOIN TRANSDIS_BILLAMORTIZATION A                     ";
        sql+="                            ON A.BILLID = B.ID                                            ";
        sql+="                         WHERE (BT.TRANSACTIONTYPE IN (102, 106) OR                       ";
        sql+="                               (BT.TRANSACTIONTYPE = 101 AND                              ";
        sql+="                               BT.TRANSDIRECTION = 1) OR                                  ";
        sql+="                               (BT.TRANSACTIONTYPE = 104 AND                              ";
        sql+="                               BT.TRANSDIRECTION = 4))                                    ";
        sql+="                           AND (A.PREDRAWINTEREST IS NULL OR                              ";
        sql+="                               A.PREDRAWINTEREST <> B.INITIALINTEREST)                    ";
        sql+="                           AND BT.TRANSACTIONDATE < TO_DATE(?, 'YYYY-MM-DD')              ";
        sql+="                           AND (A.PREDRAWDATE IS NULL OR                                  ";
        sql+="                               A.PREDRAWDATE < TO_DATE(?, 'YYYY-MM-DD'))                  ";
        sql+="                           AND B.billCODE >= ?                                            ";
        sql+="                           AND B.billCODE <= ?                                            ";
        sql+="                         ORDER BY contractCode, CODE                                      ";
        //参数
        String params = "[8940, 8940, 2023-09-20, 2023-09-20, 690764101208120230913000032019, 690764101208120230913000032019]";
        params = clearBracket(params);
        params = params.replaceAll("[\\[\\]]","");
        String[] sqlArr = sql.split("\\?");
        String[] paramArr = params.split(",");
        for (int i = 0;i < paramArr.length;i++) {
            sqlArr[i] = "\n"+sqlArr[i] + "'"+paramArr[i].trim() + "'";
        }
        String newSQL= "";
        for (int i = 0;i < sqlArr.length;i++) {
            newSQL += sqlArr[i];
        }
        System.out.println(newSQL);
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
