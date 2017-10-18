package com.paul.club.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.ClubDao;
import com.paul.club.entity.Club;

public class ClubModifyServlet extends HttpServlet{

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
			ClubDao.mergeClub(club);
			req.setAttribute("success", "俱乐部修改成功！");
		}catch(Exception e){
			req.setAttribute("error", "俱乐部修改失败！");
		}
		req.getRequestDispatcher("club").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
