package com.grouptech.trml;

import com.grouptech.trml.utility.DerbyUtils;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mahone on 2017/3/29.
 */
public class DerbyUtilsTest {

    @Test
    public void test() {
        ResultSet rs = null;
        try {

            // create a table and insert two records
            DerbyUtils.execute("create table hellotable(name varchar(40), score int)");
            System.out.println("Created table hellotable");
            DerbyUtils.execute("insert into hellotable values('Ruth Cao', 86)");
            DerbyUtils.execute("insert into hellotable values ('Flora Shi', 92)");
            // list the two records
            rs = DerbyUtils.executeQuery("SELECT name, score FROM hellotable ORDER BY score");
            System.out.println("name\t\tscore");
            while(rs.next()) {
                StringBuilder builder = new StringBuilder(rs.getString(1));
                builder.append("\t");
                builder.append(rs.getInt(2));
                System.out.println(builder.toString());
            }
            rs.close();
            // delete the table
            DerbyUtils.execute("drop table hellotable");
            System.out.println("Dropped table hellotable");

            System.out.println("Closed result set and state");
            //DerbyUtils.shutdown();

        } catch (SQLException e) {
            // handle the exception
            e.printStackTrace();
        } finally {
            // ResultSet close
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        System.out.println("SimpleApp finished");
    }
}
