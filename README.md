# MOCKSQL
随机生成数据库数据
- AutoSQL类的构造传参 需要提供表名 生成的条数
- AutoSQL类中AutoInsert无参方法 按默认配置进行随机生成数据
- AutoSQL类中AutoInsert有参方法 提供数据中需要有序生成的日期字段或者时间戳 按从小到大进行传参 string类型的参数
- 先支持的数据库字段 varchar datetime datime id自动递增
- 代码示例
> 下面例子在testAutoSQLDemo中 先运行forjdbc.sql 生成表 再进行测试
```java
        AutoSQL forjdbc = new AutoSQL("forjdbc", 10000);//forjdbc表中生成10000条数据
        forjdbc.AutoInsert("createdate","enddate","afterdate","aa","bb","cc","dd");
```
