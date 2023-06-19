package com.hzlx.utils;

import java.sql.*;
import java.util.LinkedList;

//自定义连接池
public class ConnectionPool {
    //最小连接数
    private static  int MIN_POOL_NUM;
    //最大连接数
    private static  int MAX_POOL_NUM;

    private static String DRIVER;
    private static  String URL;
    private static  String USERNAME;
    private static  String PASSWORD;
    //装链接的容器
    private static LinkedList<Connection>connectionPool=new LinkedList<Connection>();

    static {
        //初始化方法，给属性赋值
        init();
    }
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("加载数据库驱动异常");
            e.printStackTrace();
        }
        //给连接池填充连接（参照最小连接数）
        initConnection();
    }
    private static void init(){
        //先把jdbc配置文件加载到程序里
        PropertiesUtil.load("jdbc");

        //根据配置文件里的key给我们的连接池中的属性赋值
        DRIVER=PropertiesUtil.getValue("jdbc.driver");
        URL=PropertiesUtil.getValue("jdbc.url");
        USERNAME= PropertiesUtil.getValue("jdbc.userName");
        PASSWORD=PropertiesUtil.getValue("jdbc.password");
        //连接池相关的属性
        MIN_POOL_NUM=Integer.parseInt(PropertiesUtil.getValue("jdbc.minPoolNum"));
        MAX_POOL_NUM=Integer.parseInt(PropertiesUtil.getValue("jdbc.maxPoolNum"));
    }
    //给连接池初始化
    private static void initConnection(){
        for (int rows = 0; rows < MIN_POOL_NUM; rows++) {
            try {
                //没此创建一个新的连接都往集合尾部追加
                connectionPool.addLast(DriverManager.getConnection(URL,USERNAME,PASSWORD));
            } catch (SQLException e) {
                System.out.println("获取连接异常");
                e.printStackTrace();
            }
        }
    }
    public static Connection getConnection(){

        //判断连接池中是否有可用连接，如果有取出最顶上的连接，如果没有创建一个
        if (connectionPool.size()>0){
            return connectionPool.removeFirst();
        }
        try {
            return DriverManager.getConnection(URL,URL,PASSWORD);
        } catch (SQLException e) {
            System.out.println("获取连接异常");
            e.printStackTrace();
        }
        return null;
    }


    /*归还链接的方法
    connection 还回来的连接对象
    ture 归还成功 false 归还失败
     */
    private static boolean returnConnection(Connection connection){
        //当前连接池集合的size大于等于配置好最大连接数的话，说明池子满了，这个连接需要丢弃
        if (connectionPool.size()>=MAX_POOL_NUM){
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("关闭连接异常");
                e.printStackTrace();
            }
        }else {
            connectionPool.addLast(connection);
            return true;
        }
        return false;
    }
    public static boolean colesAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("关闭结果集失败");
                e.printStackTrace();

            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭preparedStatement异常");
                e.printStackTrace();
            }
        }
        return returnConnection(connection);
    }
}
