package com.paul.club.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.PlayerDao;

public class PlayerDeleteServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		try{
			PlayerDao.deletePlayer(id);
			req.setAttribute("success", "���ɾ���ɹ���");
		}catch(Exception e){
			req.setAttribute("error", "���ɾ��ʧ�ܣ�");
		}
		req.getRequestDispatcher("player").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
