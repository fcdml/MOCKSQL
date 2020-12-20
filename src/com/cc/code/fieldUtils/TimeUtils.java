package com.cc.code.fieldUtils;

import com.alibaba.fastjson.JSONObject;
import com.cc.code.Definition.DbForFieldDefinition;
import com.cc.code.autoAllUtils.MiddleTableUtils.AutoSingleUtils;
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
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        int randomMonth = (new Random()).nextInt(12) + 1;
        String randomYMD = "" + dateTime1.getYear() + "-" + randomMonth + "-" + getRandomDay(dateTime1, randomMonth);
//        String print = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime1);
        return DateTime.parse(randomYMD + getHMS(), format);
    }

    //获取只有年月日的日期----数据中的的date
    public static DateTime getDate() {
        DateTime dateTime = (new DateTime()).minusYears(10).plusYears((new Random()).nextInt(20) + 1);
        int randomMonthX = (new Random()).nextInt(12) + 1;
        String randomYMD = "" + dateTime.getYear() + "-" + randomMonthX + "-" + getRandomDay(dateTime, randomMonthX);
//        String print = DateTimeFormat.forPattern("yyyy-MM-dd").print(dateTime);
        return DateTime.parse(randomYMD, DateTimeFormat.forPattern("yyyy-MM-dd"));
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

    public static TreeMap<Integer, HashMap<String, DateTime>> getSortDateOrDateTime(String tableName, String... dateFields) {
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        TreeMap<Integer, HashMap<String, DateTime>> returnTreeMap = null;
        // 对数据库中是日期加时间(datetime) 进行计数 如果和形参 参数相同 则是全部为datetime 如果为0 则是全部为date
        // 如果卡在形参个数与0之间 则是datetime与date的混合比较
        int iDateOrDateTime = 0;
        for (String d : dateFields) {
            for (JSONObject s : allFieldDefinition) {
//                System.out.println(s.getString("columnName"));
//                System.out.println(d);
                if (s.getString("columnName").equals(d) && "datetime".equals(s.getString("dataType"))) {
                    iDateOrDateTime++;
                    break;
                }
            }
        }
        if (iDateOrDateTime == dateFields.length) {


            returnTreeMap = getSortDateTime(tableName, dateFields);
        } else if (iDateOrDateTime == 0) {
            returnTreeMap = getSortDate(tableName, dateFields);
        } else {
            returnTreeMap = getSortDateAndDateTime(tableName, dateFields);
        }
//     System.out.println("++++"+returnTreeMap);
        return returnTreeMap;
    }

    public static TreeMap<Integer, HashMap<String, DateTime>> getSortDateAndDateTime(String tableName, String[] dateFields) {
        TreeMap<Integer, HashMap<String, DateTime>> integerHashMapTreeMapDateTimeAndDate = new TreeMap<>();
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        ArrayList<DateTime> dateTimes = new ArrayList<>();
        for (String u : dateFields) {
            for (JSONObject s : allFieldDefinition) {
                if (s.getString("columnName").equals(u) && "datetime".equals(s.getString("dataType"))) {
                    dateTimes.add(getDateTime());
                    break;
                } else if (s.getString("columnName").equals(u) && "date".equals(s.getString("dataType"))) {
                    dateTimes.add(getDate());
                    break;
                }
            }
        }
//        System.out.println("-----"+dateTimes);
        ListSort(dateTimes);
//        System.out.println("++++++"+dateTimes);
        int i = 0;
        for (String u : dateFields) {
            for (JSONObject s :
                    allFieldDefinition) {
                if (u.equals(s.getString("columnName"))) {
                    Integer ordinalPosition = s.getInteger("ordinalPosition");
                    HashMap<String, DateTime> stringDateTimeHashMap = new HashMap<>();
                    stringDateTimeHashMap.put(u, dateTimes.get(i));
                    integerHashMapTreeMapDateTimeAndDate.put(ordinalPosition, stringDateTimeHashMap);
                    break;
                }
            }
            i++;
        }
// System.out.println(integerHashMapTreeMapDateTimeAndDate);
        return integerHashMapTreeMapDateTimeAndDate;
    }

    public static TreeMap<Integer, HashMap<String, DateTime>> getSortDateTime(String tableName, String[] dateFields) {
        TreeMap<Integer, HashMap<String, DateTime>> integerHashMapTreeMapDateTime = new TreeMap<>();
        //Integer 存该字段所在下标
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        ArrayList<DateTime> dateTimes = new ArrayList<>();
        for (String s : dateFields) {
            dateTimes.add(getDateTime());
        }
        ListSort(dateTimes);
        int i = 0;
        for (String u : dateFields) {
            for (JSONObject s :
                    allFieldDefinition) {
                if (u.equals(s.getString("columnName"))) {
                    Integer ordinalPosition = s.getInteger("ordinalPosition");
                    HashMap<String, DateTime> stringDateTimeHashMap = new HashMap<>();
                    stringDateTimeHashMap.put(u, dateTimes.get(i));
                    integerHashMapTreeMapDateTime.put(ordinalPosition, stringDateTimeHashMap);
                    break;
                }
            }
            i++;
        }
//        System.out.println("com.cc.fieldUtils.TimeUtils.getSortDateTime---130----->"+integerHashMapTreeMapDateTime);
        return integerHashMapTreeMapDateTime;
    }

    public static TreeMap<Integer, HashMap<String, DateTime>> getSortDate(String tableName, String[] dateFields) {
        TreeMap<Integer, HashMap<String, DateTime>> integerHashMapTreeMapDate = new TreeMap<>();
        //Integer 存该字段所在下标
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        ArrayList<DateTime> dateTimes = new ArrayList<>();
        for (String s : dateFields) {
            dateTimes.add(getDate());
        }
        ListSort(dateTimes);
        int i = 0;
        for (String u : dateFields) {
            for (JSONObject s :
                    allFieldDefinition) {
                if (u.equals(s.getString("columnName"))) {
                    Integer ordinalPosition = s.getInteger("ordinalPosition");
                    HashMap<String, DateTime> stringDateTimeHashMap = new HashMap<>();
                    stringDateTimeHashMap.put(u, dateTimes.get(i));
                    integerHashMapTreeMapDate.put(ordinalPosition, stringDateTimeHashMap);
                    break;
                }
            }
            i++;
        }
//        System.out.println("com.cc.fieldUtils.TimeUtils.getSortDate---96----->"+integerHashMapTreeMapDate);
        return integerHashMapTreeMapDate;
    }

    private static void ListSort(List<DateTime> list) {

        Collections.sort(list, new Comparator<DateTime>() {
            @Override
            public int compare(DateTime o1, DateTime o2) {
                if (o1.getMillis() > o2.getMillis()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

//        System.out.println("======"+list);
    }

    public static Boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }
        return false;
    }

    public static int getRandomDay(DateTime dateTime, int randomMonth) {
        int onlyRandomDay = 0;
        if (isLeapYear(dateTime.getYear()) && randomMonth == 2) {
            onlyRandomDay = 29;
        } else if (!isLeapYear(dateTime.getYear()) && randomMonth == 2) {
            onlyRandomDay = 28;
        } else if (randomMonth == 4 || randomMonth == 6 || randomMonth == 9 || randomMonth == 11) {
            onlyRandomDay = 30;
        } else {
            onlyRandomDay = 31;
        }
        return (new Random().nextInt(onlyRandomDay))+1;
    }
    public static int getRandomDay(Integer year, int randomMonth) {
        int onlyRandomDay = 0;
        if (isLeapYear(year) && randomMonth == 2) {
            onlyRandomDay = 29;
        } else if (!isLeapYear(year) && randomMonth == 2) {
            onlyRandomDay = 28;
        } else if (randomMonth == 4 || randomMonth == 6 || randomMonth == 9 || randomMonth == 11) {
            onlyRandomDay = 30;
        } else {
            onlyRandomDay = 31;
        }
            return (new Random().nextInt(onlyRandomDay))+1;
    }
    // REM 用于指定日期区间的使用
    public static int getRandomDay(Integer year, int randomMonth,int currentDay,int flagMinAndMax) {
        int onlyRandomDay = 0;
        if (isLeapYear(year) && randomMonth == 2) {
            onlyRandomDay = 29;
        } else if (!isLeapYear(year) && randomMonth == 2) {
            onlyRandomDay = 28;
        } else if (randomMonth == 4 || randomMonth == 6 || randomMonth == 9 || randomMonth == 11) {
            onlyRandomDay = 30;
        } else {
            onlyRandomDay = 31;
        }
        if (flagMinAndMax==1){
            // 生成比当前天数大的随机天数
            return currentDay+(new Random().nextInt(onlyRandomDay-currentDay+1));
        }else if (flagMinAndMax==-1) {
            return 1 + (new Random().nextInt(currentDay + 1));
        }
        // TODO 待判断逻辑是否严谨
        return 0;
    }
    //获取时分秒的随机数
    public static String getHMS() {
        int randomHour = new Random().nextInt(24);
        int randomMinutes = new Random().nextInt(60);
        int randomSeconds = new Random().nextInt(60);
        return " " + randomHour + ":" + randomMinutes + ":" + randomSeconds;
    }
    public static String getHMS(String[] s,Boolean flagHSM) {
        int randomHour =0;
        int randomMinutes =0;
        int randomSeconds =0;

        if (flagHSM){
            randomHour = Integer.parseInt(s[0])+new Random().nextInt(24-Integer.parseInt(s[0]));
            if (Integer.parseInt(s[0])==randomHour){
                randomMinutes = Integer.parseInt(s[1])+ new Random().nextInt(60 -Integer.parseInt(s[1])+1);
                if (Integer.parseInt(s[1])==randomMinutes){
                    randomSeconds = Integer.parseInt(s[2])+new Random().nextInt(60-Integer.parseInt(s[2])+1);
                }else {
                    randomSeconds = new Random().nextInt(60);
                }
            }else {
                randomMinutes = new Random().nextInt(60);
                randomSeconds = new Random().nextInt(60);
            }
        }else {
            randomHour =new Random().nextInt(Integer.parseInt(s[0]));
            if (Integer.parseInt(s[0])==randomHour){
                randomMinutes =  new Random().nextInt(Integer.parseInt(s[1]));
                if (Integer.parseInt(s[1])==randomMinutes){
                    randomSeconds = new Random().nextInt(Integer.parseInt(s[2]));
                }else {
                    randomSeconds = new Random().nextInt(60);
                }
            }else {
                randomMinutes = new Random().nextInt(60);
                randomSeconds = new Random().nextInt(60);
            }
        }
        return " " + randomHour + ":" + randomMinutes + ":" + randomSeconds;
    }

    public static DateTime getDatTimeByInterval(String beginDateTime, String endDateTime) {
        System.out.println("beginDateTime = " + beginDateTime + ", endDateTime = " + endDateTime);
        String[] stringBeginDateTime = AutoSingleUtils.getStringDateTime(beginDateTime);
        String[] stringBeginSplitDate = AutoSingleUtils.getStringSplitDate(stringBeginDateTime != null ? stringBeginDateTime[0] : null);
        String[] stringBeginSplitTime = AutoSingleUtils.getStringSplitTime(stringBeginDateTime != null ? stringBeginDateTime[1] : null);
        String[] stringEndDateTime = AutoSingleUtils.getStringDateTime(endDateTime);
        String[] stringEndSplitDate = AutoSingleUtils.getStringSplitDate(stringEndDateTime != null ? stringEndDateTime[0] : null);
        String[] stringEndSplitTime = AutoSingleUtils.getStringSplitTime(stringEndDateTime != null ? stringEndDateTime[1] : null);
        Integer year = getNumber(Integer.valueOf(stringBeginSplitDate[0]), Integer.valueOf(stringEndSplitDate[0]));
        String beginRandomDateTime ="";
        if (year > Integer.parseInt(stringBeginSplitDate[0]) && year < Integer.parseInt(stringEndSplitDate[0])) {
            beginRandomDateTime += year;
            System.out.println("++++++++++"+beginRandomDateTime);
            Integer randomMonth = getRandomMonth();
            beginRandomDateTime += "-" + randomMonth + "-" + getRandomDay(year,randomMonth);
            beginRandomDateTime+=" "+getHMS();
        }else if (year.equals(Integer.parseInt(stringBeginSplitDate[0]))){
            System.out.println("((((((((((((((((((");
            beginRandomDateTime += year;
            Random random = new Random();
            Integer currentMonth = Integer.valueOf(stringBeginSplitDate[1]);
            Integer randomMonth =1+random.nextInt(currentMonth);
            beginRandomDateTime+="-"+randomMonth;
            if (randomMonth.equals(currentMonth)){
                int randomDay = getRandomDay(year, randomMonth, Integer.parseInt(stringEndSplitDate[2]), 1);
                if (randomDay==Integer.parseInt(stringEndSplitDate[2])){
                    beginRandomDateTime+="-"+randomDay;
                }
                beginRandomDateTime+="-"+getRandomDay(year,randomMonth,Integer.parseInt(stringEndSplitDate[2]),1);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               beginRandomDateTime+=getHMS( stringBeginSplitTime ,true);
             }else {
                beginRandomDateTime+="-"+ getRandomDay(year,randomMonth);
                beginRandomDateTime+=getHMS();
             }
        }else if (year.equals(Integer.parseInt(stringEndSplitDate[0]))){
            System.out.println(")))))))))))))))))))))))");
            beginRandomDateTime += year;
            Random random = new Random();
            Integer currentMonth = Integer.valueOf(stringEndSplitDate[1]);
            Integer randomMonth =1+random.nextInt(currentMonth);
            beginRandomDateTime+="-"+randomMonth;
            if (randomMonth.equals(currentMonth)){
                beginRandomDateTime+="-"+getRandomDay(year,randomMonth,Integer.parseInt(stringEndSplitDate[2]),-1);
                beginRandomDateTime+=getHMS( stringBeginSplitTime ,false);
            }else {
                beginRandomDateTime+="-"+ getRandomDay(year,randomMonth);
                beginRandomDateTime+=getHMS();
            }
        }
        System.out.println("=============================="+beginRandomDateTime);
        DateTime  begindateTime= AutoSingleUtils.getDateTime(beginRandomDateTime);
        return begindateTime;
    }

    public static DateTime getDateByInterval(String beginDate, String endDate) {
        DateTime begindateTime = AutoSingleUtils.getDateTime(beginDate);
        DateTime enddateTime = AutoSingleUtils.getDateTime(endDate);
        return null;
    }

    public static Integer getNumber(Integer begin, Integer end) {
        System.out.println("begin = " + begin + ", end = " + end);
        Random random = new Random();
        Integer randomNum = begin + random.nextInt(end - begin + 1);
        return randomNum;
    }

    public static Integer getRandomMonth() {
        Random random = new Random();
        Integer randomNum = 1 + random.nextInt(12);
        return randomNum;
    }

//    public static void main(String[] args) {
//        DateTime dateTime = new DateTime();
//        for (int i = 0; i <1000 ; i++) {
//            int randomDay = getRandomDay(dateTime, 2);
//            if (randomDay==0){
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                break;
//            }
//            System.out.println(randomDay);
//        }
//
//    }


}
