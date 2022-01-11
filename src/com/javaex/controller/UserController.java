package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	
  

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	System.out.println("user");
	String act = request.getParameter("action");
	
		if("joinForm".equals(act)) {
			System.out.println("user > joinfrom");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		
		
		
		}else if("join".equals(act)) {
			System.out.println("user > join");
			//파라미터 가져오기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			
			//파라미터 -->vo로 만들기
			UserVo uservo = new UserVo(id, password, name, gender);
			//System.out.println(uservo);
			
			//userdao--저장하기
			UserDao userDao = new UserDao();
			userDao.userinsert(uservo);
		
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinOk.jsp");//포워드는 내부 직원,에게 모든걸 준다
			
			
		}else if("loginForm".equals(act)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(act)) {
			
		}
		
		
		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
