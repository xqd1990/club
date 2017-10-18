package com.paul.club.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.ClubDao;
import com.paul.club.entity.Club;

public class ClubAddServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Club club = new Club();
		club.setId(req.getParameter("id"));
		club.setName(req.getParameter("name"));
		club.setPrincipal(req.getParameter("principal"));
		club.setStatement(req.getParameter("statement"));
		club.setInterest(Double.valueOf(req.getParameter("interest")));
		club.setNameInItaly(req.getParameter("nameInItaly"));
		club.setWechat(req.getParameter("wechat"));
		club.setExtra(Double.valueOf(req.getParameter("extra")));
		club.setState(Integer.valueOf(req.getParameter("state")));
		try{
			ClubDao.addClub(club);
			req.setAttribute("success", "新增俱乐部成功！！！");
		}catch(RuntimeException e){
			req.setAttribute("error", "新增俱乐部失败！！！");
		}
		req.getRequestDispatcher("club").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
