package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	
  

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	System.out.println("user");
	String act = request.getParameter("action");
	request.setCharacterEncoding("UTF-8");
	
	
	
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
		
			String id = request.getParameter("id");
			String password = request.getParameter("password");
	
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, password);//정상로그인시 아디패스워드를 authVo에 넣음
			
			
			
			
			if(authVo == null) { //로그인 실패시
			
				System.out.println("로그인 실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");//이거 오늘 숙제
				
				
			}else {//로그인 성공시
				System.out.println("로그인 성공");
				
				HttpSession session = request.getSession(); //세션 영역에 리퀘스트로 값 가져오기를 하겠다            인터넷이랑 톰켓이 주고받는 쇼핑카트
				session.setAttribute("authUser", authVo); //세션에 어트리뷰트(개발자의 정보 저장공간에)를 넣겠다		안에 개인정보를 담다
				
				WebUtil.redirect(request, response, "/mysite/main");
			}
			
			
		
		}else if("logout".equals(act)) {
			System.out.println("로그아웃");
			
			HttpSession session = request.getSession();															//그리고 이건 그 쇼핑카트안에서 지운다
			session.removeAttribute("authUser");
			session.invalidate();                        //로그인 전으로 되돌리겠다
			WebUtil.redirect(request, response, "/mysite/main");
		}else if("modifyForm".equals(act)) {
			System.out.println("모디폼으로 진입 성공");	
			
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);	
			
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			
			
		}else if("modify".equals(act)) {
			System.out.println("모디파이 도킹 성공");
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			int no = Integer.parseInt(request.getParameter("no"));
			
			UserVo userVo = new UserVo(no, id, password, name, gender);
			UserDao userDao = new UserDao();
			
			userDao.userModify(userVo);
			UserVo authVo = new UserVo();
			authVo.setNo(userVo.getNo());
			authVo.setName(userVo.getName());
			WebUtil.redirect(request, response, "/mysite/main");
			
			HttpSession session = request.getSession(); //지금 세션 값을 줘
			session.setAttribute("authUser", authVo);//호출할 이름, 넣을 변수
			
			System.out.println("정보수정 완료");
		}
		
		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
