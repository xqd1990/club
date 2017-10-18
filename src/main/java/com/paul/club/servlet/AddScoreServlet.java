package com.paul.club.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paul.club.dao.ScoreDao;
import com.paul.club.entity.Score;

public class AddScoreServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> teams = ScoreDao.getTeams();
		req.setAttribute("teams", teams);
		String date = req.getParameter("date");
		String team = req.getParameter("team");
		String name = req.getParameter("name");
		String score = req.getParameter("score");
		if(date!=null&&!"".equals(date)&&team!=null&&!"".equals(team)&&name!=null&&!"".equals(name)&&score!=null&&!"".equals(score)){
			Score s = new Score();
			s.setDate(date);
			s.setTeam(team);
			s.setName(name);
			try{
				s.setScore(Integer.valueOf(score));
				ScoreDao.addScoreRecord(s);
				req.setAttribute("success", "添加成功!");
			}catch(Exception e){
				req.setAttribute("error", "添加失败！");
			}
		}
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		if(startDate!=null&&!"".equals(startDate)&&endDate!=null&&!"".equals(endDate)){
			req.setAttribute("scores", ScoreDao.getScoresByDate(startDate, endDate));
		}
		req.getRequestDispatcher("addScore.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
