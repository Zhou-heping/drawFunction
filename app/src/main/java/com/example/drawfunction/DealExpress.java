package com.example.drawfunction;

import java.util.List;
import java.util.Scanner;

public class DealExpress {
    private String express ;// 表达式
    private String var ; // 变量名，这里只实现1个变量名，如果输入常数则默认为 x = 常数
    private boolean firstIsAble1 ;
    private boolean firstIsAble2 ;
    private Calculator cal = new Calculator();// 计算器
    /*
    public static void main(String[] args) {
        String express;
        //System.out.println("请输入表达式：");
        Scanner input = new Scanner(System.in);
        express = input.nextLine();
        //System.out.println("输入表达式为：" + express);
        DealExpress de = new DealExpress(express, "x");
        System.out.println(de.dealWithExpress(-10));

    }
    */
    public DealExpress(String express, String var) {
        this.express = express;
        this.var = var;
        firstIsAble1 = true;
        firstIsAble2 = true;
    }

    // 处理表达式，并通过传入的x值，求出y的值
    // 实现方案，先不计算表达式的值，只判断表达式是否合理，以及规范表达式。当实际传入参数的时候才计算出实际值
    // 如果表达式不合法则返回0；
    public double dealWithExpress(double var_x) {
        double result = 0;
        // 去除空格
        express = express.replaceAll(" ", "");
        // 判断输入字符是否合法
        boolean flag = isMathsOthers();// 只判断一次，如果为真，则之后一直为真

        if (flag) {
            // 如果为空串，返回0
            if (express.equals("")) {
                return 0;
            }
            // 如果为数字，则是常量函数，即平行于y轴的函数,则返回常数
            else if (express.matches("^[-+]?[0-9]+(\\.[0-9]+)?$")) {
                return Double.parseDouble(express);
            }
            // 其他情况，含有x的表达式;
            // 不含x，但是可能是常量，如sin(2)等
            else {
                // 处理表达式，直接为x添加括号，后续处理再计算器中实现(出现”数字x“ 转为 ”数字*x“，或者)x转为)*x)
                express = dealStandardFormat();//添加括号，只添加一次，后续再调用则不添加
                String oldExpress = express;//保存表达式
                if (var.charAt(0) == '-') {
                    var = 0 + var;
                }
                express = express.replaceAll("x", var_x + "");// 转化为输入的数
                express = express + "=";
                express = cal.Judge(express);
                if (express.equals("表达式错误")) {
                    return 0;
                } else {
                    List<String> list = cal.exchange(express);
                    result = Double.parseDouble(cal.doCalculator(list));
                    express = oldExpress;
                    return result;
                }
            }
        }
        return result;
    }

    // 匹配+-*/,数字，字符串：sin,cos,tan,x,ln,!,%,^,(,), 小数点.
    public boolean isMathsOthers() {
        if (firstIsAble1) {
            // 将字符串转化为空，减少后续判断量
            String s = express;
            s = s.replaceAll("sin", "");
            s = s.replaceAll("cos", "");
            s = s.replaceAll("tan", "");
            s = s.replaceAll("log", "");
            for (int i = 0; i < s.length(); i++) {
                if (!(cal.isNumber(s.charAt(i)) || cal.isOperator(s.charAt(i)) || isOthersOp(s.charAt(i)))) {
                    return false;
                }
            }
            firstIsAble1 = false;
        }
        return true;
    }

    public String dealStandardFormat() {
        if (firstIsAble2) {
            StringBuilder sb = new StringBuilder();// 可变字符序列
            char c;
            for (int i = 0; i < express.length(); i++) {
                c = express.charAt(i);
                if (c == 'x') {
                    sb.append("(x)");
                    continue;
                }
                sb.append(c);
            }
            express = sb.toString();
            firstIsAble2 = false;
        }

        return express;
    }

    public boolean isOthersOp(char c) {
        if (c == 'x' || c == '%' || c == '!' || c == '(' || c == ')' || c == '^' || c == '.') {
            return true;
        }
        return false;
    }
}
