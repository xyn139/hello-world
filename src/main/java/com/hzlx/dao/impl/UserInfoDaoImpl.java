package com.hzlx.dao.impl;

import com.hzlx.dao.UserinfoDao;
import com.hzlx.entity.UserInfo;
import com.hzlx.utils.BaseDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDaoImpl extends BaseDao<UserInfo> implements UserinfoDao {

    @Override
    public List<UserInfo> getUserInfoAll() {
 /*       String sql="select * from user_info";
        return selectListForObject(sql,UserInfo.class);*/
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            DriverManager.registerDriver(driver);
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/background-management-system?useSSL=false&useUniCode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                    "root", "Xyn139488_");

            PreparedStatement preparedStatement = connection.prepareStatement("select * from user_info");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt(1));
                userInfo.setUserName(resultSet.getString(2));
                userInfo.setPassword(resultSet.getString(3));
                userInfo.setNickName(resultSet.getString(4));
                userInfo.setTel(resultSet.getString(5));
                userInfo.setAddress(resultSet.getString(6));
                userInfo.setCreationTime(resultSet.getDate(7));
                userInfo.setStatus(resultSet.getInt(8));
                userInfos.add(userInfo);
            }
            return userInfos;
        } catch (Exception e) {
            return null;
        }
    }
}
