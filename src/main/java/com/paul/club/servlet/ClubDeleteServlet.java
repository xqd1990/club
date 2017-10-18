package com.paul.club.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.ClubDao;

public class ClubDeleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		try{
			ClubDao.deleteClub(id);
			req.setAttribute("success", "É¾³ý³É¹¦£¡");
		}catch(Exception e){
			req.setAttribute("error", "É¾³ýÊ§°Ü£¡");
		}
		req.getRequestDispatcher("club").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
