package com.paul.club.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.PlayerDao;
import com.paul.club.dao.ScoreDao;
import com.paul.club.entity.Player;
import com.paul.club.util.DataStandardInterface;

public class PlayerServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int page = 1;
		String request_page = req.getParameter("request_page");
		if(request_page!=null){
			page = Integer.valueOf(request_page);
		}
		List<Player> players = PlayerDao.getPlayers(page);
		req.setAttribute("teams", ScoreDao.getTeams());
		req.setAttribute("players", players);
		req.setAttribute("currentpage", page);
		req.setAttribute("lastpage", (PlayerDao.getPlayerNumber()/DataStandardInterface.PLAYER_PAGE_COUNT+1));
		req.getRequestDispatcher("player.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
