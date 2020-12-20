package test;
import com.cc.code.autoAllSQL.AutoSingleSQL;
import com.cc.code.connectionUtils.DruidUtils;
/**
 * @Classname testSingleAutoSQLDemo
 * @Description TODO
 * @Date 2020/12/13 14:03
 * @Created by 2413776263@qq.com
 */
public class testSingleAutoSQLDemo {
    public static void main(String[] args) {
        //deptsome forjdbc
        //TODO  更新数据库文件 forjdbc.sql
        DruidUtils.initSource("jdbc:mysql://localhost:3306/mocksql?useSSL=false&characterEncoding=utf8&serverTimeZone=GMT+8&allowMultiQueries=true&rewriteBatchedStatements=true",
                             "root","mysql9614");
        AutoSingleSQL forjdbc = new AutoSingleSQL("forjdbc", 20);
//        forjdbc.autoInsertS();
        forjdbc.autoInsertS("createdate","enddate","afterdate","mydate","aa","bb","cc","dd");
    }
}
