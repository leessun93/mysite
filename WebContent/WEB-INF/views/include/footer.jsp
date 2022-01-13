<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %> 
 <%
	UserVo authUser = (UserVo)session.getAttribute("authUser"); //반쯤 외워버리긔

%>    
		<div id="footer">
			Copyright ⓒ 2022 이선흠. All right reserved
		</div>