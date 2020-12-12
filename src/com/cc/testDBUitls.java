package com.cc;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname com.cc.testDBUitls
 * @Description TODO
 * @Date 2020/12/11 21:46
 * @Created by 2413776263@qq.com
 */
public class testDBUitls {
    public static void main(String[] args) throws ClassNotFoundException {
//        com.cc.DBUitls dbUitls = new com.cc.DBUitls(com.cc.FORJDBC.class,6);
//        Field[] declaredFields1 = Class.forName("com.cc.FORJDBC").getDeclaredFields();
//        Field[] declaredFields = Class.forName("com.").getDeclaredFields();
//        System.out.println(Arrays.toString(declaredFields));
//        for (Field f:
//             declaredFields) {
//            System.out.println(f.getType().getTypeName());
//  if (f.getType().getTypeName().equals("java.lang.String"))
//      System.out.println(2222);
//        }
//        Random random = new Random();

//        for (int j = 0; j <100; j++) {
//            int i = random.nextInt(10);
//            System.out.println(i);
//            if (i==10){
//                System.out.println("=========================================================================");
//            }
//        }
//        String[] strings= {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
//
//        for (int j = 0; j <20 ; j++) {
//            String u="";
//            for (int k = 0; k <6 ; k++) {
//                String string =strings[random.nextInt(26)];
//                     u+=string;
////                System.out.println(u);
//            }
//            System.out.println(u);
//        }
//        String s="'...'";
//        Pattern compile = Pattern.compile(s);
//        Matcher matcher = compile.matcher("enum('转让中','已转让','持有中')");
//        while (matcher.find()){
//            System.out.println(matcher.group().substring(1,matcher.group().length()-1));
//        }
//        System.out.println("enum('转让中','已转让','持有中')".matches(s));
//        String[] split = "enum('转让中','已转让','持有中')".split(",");
////        System.out.println(Arrays.toString(split));
//        for (int i = 0; i <split.length ; i++) {
//            System.out.println(split[i]);
//        }
//        System.out.println(FORJDBC.class.getName());
//      /  DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
//        HashMap<String, ArrayList<String>> anEnum = dbForEnum.getEnum();
//        for (Map.Entry<String, ArrayList<String>>  u:
//                anEnum.entrySet()) {
//                System.out.println(u.getKey());
//                System.out.println("size-----"+u.getValue().size());
//                for (int j = 0; j <u.getValue().size() ; j++) {
//                    System.out.println("enum--------->"+u.getValue().get(j));
//                }
//            }
        DBUitls dbUitls = new DBUitls(FORJDBC.class, 10);
        dbUitls.AutoInsert();
    }
}
