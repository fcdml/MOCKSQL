# MOCKSQL
随机生成数据库数据
- AutoSingleSQL类的构造传参 需要提供表名 生成的条数
- AutoSingleSQL类中autoInsertS无参方法 按默认配置进行随机生成数据
- AutoSingleSQL类中autoInsertS有参方法 提供数据中需要有序生成的日期字段或者时间戳 按从小到大进行传参 string类型的参数
- 先支持的数据库字段 varchar datetime datime id自动递增
- 代码示例
> 下面例子在testAutoSingleSQLDemo中 先运行forjdbc.sql 生成表 再进行测试
```java
       DruidUtils.initSource("jdbcurl",
                             "数据库用户名","数据库密码");
```
```java
        AutoSingleSQL forjdbc = new AutoSingleSQL("forjdbc", 10000);//forjdbc表中生成10000条数据
        forjdbc.autoInsertAll("createdate","enddate","afterdate","aa","bb","cc","dd");
```
```java
          字段名如:somedatetime
          日期或时间或时间戳区间:"2020-1-1:00:00:11"到"2020-1-2 8:1:1"
        AutoSingleSQL forjdbc = new AutoSingleSQL("forjdbc", 10000);//forjdbc表中生成10000条数据
        forjdbc..setAutoDateTimeInterval("somedatetime","2020-1-1:00:00:11","2020-1-2 8:1:1");
         forjdbc.autoInsertAllNoRelation();
```

 
