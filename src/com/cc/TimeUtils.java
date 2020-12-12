package com.cc;

import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

/**
 * @Classname TimeUtils
 * @Description TODO
 * @Date 2020/12/12 17:01
 * @Created by 2413776263@qq.com
 */
public class TimeUtils {
    //TODO joda DateTime 可以传参 来实现随机数 但是对闰年没有自动选择符合的日期
    //获取年月日时分秒的日期----数据中的的datetime
    public static DateTime getDateTime() {
        DateTime dateTime1 = (new DateTime()).minusYears(10).plusYears((new Random()).nextInt(20) + 1);
        DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
        int randomMonth = (new Random()).nextInt(12) + 1;
        String randomYMD=""+dateTime1.getYear()+"-"+randomMonth+"-"+getRandomDay(dateTime1,randomMonth);
//        String print = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime1);
        return DateTime.parse(randomYMD + getHMS(), format);
    }
    //获取只有年月日的日期----数据中的的date
    public static DateTime getDate() {
        DateTime dateTime = (new DateTime()).minusYears(10).plusYears((new Random()).nextInt(20) + 1);
        int randomMonthX = (new Random()).nextInt(12) + 1;
        String randomYMD=""+dateTime.getYear()+"-"+randomMonthX+"-"+getRandomDay(dateTime,randomMonthX);
//        String print = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime);
        return DateTime.parse(randomYMD,DateTimeFormat .forPattern("yyyy-MM-dd"));
    }
    //获取前前(传进来负数)后(传进来正数)几年的随机数
    //TODO 考虑yearNum为0的情况
//    public static DateTime getDateTimeAgoOrAfter(int yearNum) {
//        DateTime dateTime=null;
//        if (yearNum>0){
//            dateTime=(new DateTime()).plusYears((new Random()).nextInt(yearNum) + 1);
//        }
//        if (yearNum<0){
//            dateTime=(new DateTime()).minusYears(yearNum).plusYears((new Random()).nextInt(yearNum) + 1);
//        }
//        if (yearNum==0){
//            dateTime= new DateTime();
//        }
//        DateTime dateTime1 = (new DateTime()).minusYears(yearNum).plusYears((new Random()).nextInt(yearNum) + 1);
//        DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
//        int randomMonth = (new Random()).nextInt(12) + 1;
//        String randomYMD=""+dateTime1.getYear()+"-"+randomMonth+"-"+getRandomDay(dateTime1,randomMonth);
////        String print = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime1);
//        return DateTime.parse(randomYMD + getHMS(), format);
//    }
//    //获取后几年的随机数
//    public static DateTime getDateTimeAfter(int yearNum){
//        DateTime dateTime1 = (new DateTime()).plusYears((new Random()).nextInt(yearNum) + 1);
//        DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
//        int randomMonth = (new Random()).nextInt(12) + 1;
//        String randomYMD=""+dateTime1.getYear()+"-"+randomMonth+"-"+getRandomDay(dateTime1,randomMonth);
////        String print = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime1);
//        return DateTime.parse(randomYMD + getHMS(), format);
//    }
    public static TreeMap<Integer, HashMap<String,DateTime>> getSortDateTime(String dateField1,String dateField2,String dateField3){
        TreeMap<Integer, HashMap<String, DateTime>> integerHashMapTreeMap = new TreeMap<>();
        //Integer 存该字段所在下标
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition();
        ArrayList<DateTime> dateTimes = new ArrayList<>();
        dateTimes.add(getDateTime());
        dateTimes.add(getDateTime());
        dateTimes.add(getDateTime());
        ListSort(dateTimes);
        for (JSONObject s:
                allFieldDefinition) {
            if (s.getString("columnName").equals(dateField1)) {
                Integer ordinalPosition = s.getInteger("ordinalPosition");
                HashMap<String, DateTime> stringDateTimeHashMap = new HashMap<>();
                stringDateTimeHashMap.put(dateField1, dateTimes.get(0));
                integerHashMapTreeMap.put(ordinalPosition, stringDateTimeHashMap);
            }
            if (s.getString("columnName").equals(dateField2)) {
                Integer ordinalPosition = s.getInteger("ordinalPosition");
                HashMap<String, DateTime> stringDateTimeHashMap = new HashMap<>();
                stringDateTimeHashMap.put(dateField2, dateTimes.get(1));
                integerHashMapTreeMap.put(ordinalPosition, stringDateTimeHashMap);
            }
            if (s.getString("columnName").equals(dateField3)) {
                Integer ordinalPosition = s.getInteger("ordinalPosition");
                HashMap<String, DateTime> stringDateTimeHashMap = new HashMap<>();
                stringDateTimeHashMap.put(dateField3,  dateTimes.get(2));
                integerHashMapTreeMap.put(ordinalPosition, stringDateTimeHashMap);
            }
        }
        System.out.println("999"+integerHashMapTreeMap);
        return  integerHashMapTreeMap;
    }
    private static void ListSort(List<DateTime> list) {
        {    //排序方法
            Collections.sort(list, new Comparator<DateTime>() {
                @Override
                public int compare(DateTime o1, DateTime o2) {
                    if (o1.getMillis()>o2.getMillis()){
                        return 1;
                    }else {
                        return -1;
                    }
                }
            });
        }
    }
    public static Boolean isLeapYear(int year ){
        if (year%4==0&&year%100!=0||year%400==0){
            return true;
        }
        return false;
    }
    public static  int getRandomDay(DateTime dateTime ,int randomMonth){
        int onlyRandomDay=0;
        if (isLeapYear(dateTime.getYear())&&randomMonth==2){
            onlyRandomDay=29;
        }else if (!isLeapYear(dateTime.getYear())&&randomMonth==2){
            onlyRandomDay=28;
        }else if (randomMonth==4||randomMonth==6||randomMonth==9||randomMonth==11){
            onlyRandomDay=30;
        }else {
            onlyRandomDay=31;
        }
        return  onlyRandomDay;
    }
    //获取时分秒的随机数
    public  static  String getHMS(){
        int randomHour = new Random().nextInt(24);
        int randomMinutes = new Random().nextInt(60);
        int randomSeconds= new Random().nextInt(60);
        return " "+randomHour+":"+randomMinutes+":"+randomSeconds;
    }
//    public static void main(String[] args) {
//        ArrayList<DateTime> dateTimes = new ArrayList<>();
//        dateTimes.add(getDateTime());
//        dateTimes.add(getDateTime());
//        dateTimes.add(getDateTime());
//        ListSort(dateTimes);
//        System.out.println(dateTimes.get(0));
//        System.out.println(dateTimes.get(1));
//        System.out.println(dateTimes.get(2));
//    }
}
