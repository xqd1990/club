package com.paul.club.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.PlayerDao;
import com.paul.club.entity.Player;

public class PlayerModifyServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Player player = new Player();
		player.setId(req.getParameter("id"));
		player.setName(req.getParameter("name"));
		player.setAccount(req.getParameter("account"));
		player.setTeam(req.getParameter("team"));
		player.setInterest(Double.valueOf(req.getParameter("interest")));
		String tel = req.getParameter("tel");
		if(!"".equals(tel)){
			player.setTel(tel);
		}
		player.setPassword(req.getParameter("password"));
		try{
			PlayerDao.modifyPlayer(player);
			req.setAttribute("success", "玩家修改成功！");
		}catch(Exception e){
			req.setAttribute("error", "玩家修改失败！");
		}
		req.getRequestDispatcher("player").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
