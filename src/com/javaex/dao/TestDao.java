package com.javaex.dao;

import com.javaex.vo.UserVo;

public class TestDao {

	public static void main(String[] args) {
		UserVo uservo = new UserVo("ccc", "1234", "갱호동", "male");
		
		
		
		
		
		UserDao userDao = new UserDao();
		userDao.userinsert(uservo);
	}

}
