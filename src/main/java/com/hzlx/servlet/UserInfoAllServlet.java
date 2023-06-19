package com.hzlx.servlet;


import com.google.gson.Gson;
import com.hzlx.dao.UserinfoDao;
import com.hzlx.dao.impl.UserInfoDaoImpl;
import com.hzlx.entity.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userAll")
public class UserInfoAllServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserinfoDao userinfoDao=new UserInfoDaoImpl();
        List<UserInfo> userInfo=userinfoDao.getUserInfoAll();

        //设置响应编码格式为UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(new Gson().toJson(userInfo));
    }
}
