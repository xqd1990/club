package com.paul.club.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.BillDao;

public class BillDeleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		try{
			BillDao.deleteBills(start, end);
			req.setAttribute("success", "É¾³ý³É¹¦£¡");
		}catch(Exception e){
			req.setAttribute("error", "É¾³ýÊ§°Ü£¡");
		}
		req.getRequestDispatcher("bill.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
