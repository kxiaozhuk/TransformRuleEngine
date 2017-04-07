package com.grouptech.trml.utility;

import java.sql.*;
import java.util.Properties;

/**
 * @author zhuwenhong on 2017/3/29.
 */
public class DerbyUtils {

    private DerbyUtils(){
    }

    private final static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private final static String protocol = "jdbc:derby:";
    private final static String dbName = "habotDB";
    private static Connection conn = connection();
    private static Statement state = null;

    private static void loadDriver() {
        try {
            Class.forName(driver).newInstance();
            System.out.println("Load the embedded driver");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection connection(){
        // load the driver
        loadDriver();
        Properties props = new Properties();
        props.put("user", "habot");
        props.put("password", "habot");
        //create and connect the database named habotDB
        try {
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
            System.out.println("create and connect to habotDB");
            conn.setAutoCommit(false);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return conn;
    }

    private static Statement statement(){
        Statement s = null;
        try{
            if (state == null)
                s = conn.createStatement();
            else
                s = state;
        } catch (SQLException e) {
            // handle the exception
            e.printStackTrace();
            // Statements close
            try {
                if (s != null) {
                    s.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return s;
    }

    public static boolean isTableExist(String tableName) throws SQLException{
        if(conn!=null)
        {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(),null);
            if(rs.next())
                return true;
        }
        return false;
    }

    public static void execute(String sqlText) throws SQLException{
        Statement s = null;
        // create a table and insert two records
        s = statement();
        s.execute(sqlText);
        System.out.println("Sql text executed: " + sqlText);
    }

    public static ResultSet executeQuery(String sqlText) throws SQLException{
        // create a table and insert two records
        ResultSet rs = statement().executeQuery(sqlText);
        System.out.println("Sql query executed: " + sqlText);
        // Return resultSet
        return rs;
    }

    public static void commit() throws SQLException{
        conn.commit();
    }

    public static void shutdown(){
        try {
            // perform a clean shutdown
            DriverManager.getConnection(protocol + dbName + ";shutdown=true");
        } catch (SQLException se) {
            if (( (se.getErrorCode() == 50000) && ("XJ015".equals(se.getSQLState()) ))) {
                // we got the expected exception
                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected SQL state is "08006", and the error code is 45000.
            } else {
                // if the error code or SQLState is different, we have an unexpected exception (shutdown failed)
                System.err.println("Derby did not shut down normally");
                se.printStackTrace();
            }
        }
    }



}
