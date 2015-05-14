import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 * Created by seach_000 on 5/4/2015.
 */


public class TestConnection {
    public static void main(String[] args) throws Exception
    {


       // String temp = System.getProperty("java.class.path");
      //  Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test0.db");
        Statement stat = conn.createStatement();
/*
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("create table people (name, city);");
        stat.executeUpdate("insert into people (name, city) values  ('Christian', 'Cheney');");
        stat.executeUpdate("insert into people (name, city) values ('Max', 'Airway');");*/
        ResultSet result;
        result = stat.executeQuery("Select * from people");
      //  System.out.println(result.getString("name"));
        System.out.println(result.getString("name"));System.out.println(result.getString("name"));
    }
}
