package javas;


import kotlins.StringFunctions;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        String ipAddress = "192.168.32.254";
        String s = ipAddress.replace(".", "");
        System.out.println(s);
        String[] split = "12.345".split("\\.");//error  "."在这里是表示任意字符的正则表达式
        System.out.println(split[0]);
    }
}
