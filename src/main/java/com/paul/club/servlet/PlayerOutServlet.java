package com.paul.club.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.paul.club.dao.PlayerDao;
import com.paul.club.entity.Player;

public class PlayerOutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String flag = req.getParameter("flag");
		if("1".equals(flag)){
			playerOutOne(req);
		}else if("2".equals(flag)){
			playerOutTwo(req);
		}else{
			playerOutThree(req);
		}
		req.getRequestDispatcher("player").forward(req, resp);
	}
	
	private void playerOutOne(HttpServletRequest req){
		try{
			List<Player> players = PlayerDao.getPlayers();
			File file = new File("C:/excel/players_ID&NAME.xls");
			if(file.exists()) file.delete();
			WritableWorkbook wwb = Workbook.createWorkbook(file);
			WritableSheet ws = wwb.createSheet("所有玩家信息", 0);
			ws.setColumnView(1, 15);
			ws.setColumnView(2, 15);
			ws.addCell(new Label(0, 0, "团队"));
			ws.addCell(new Label(1, 0, "ID"));
			ws.addCell(new Label(2, 0, "账号"));
			int count = 1;
			for(Player player:players){
				ws.addCell(new Label(0, count, player.getTeam()));
				ws.addCell(new Label(1, count, player.getId()));
				ws.addCell(new Label(2, count, player.getAccount()));
				count++;
			}
			wwb.write();
			wwb.close();
			req.setAttribute("success", "导出玩家信息成功！");
		}catch(Exception e){
			req.setAttribute("success", "导出玩家信息失败！");
		}
	}
	private void playerOutTwo(HttpServletRequest req){
		try{
			List<Player> players = PlayerDao.getPlayers();
			File file = new File("C:/excel/players.xls");
			if(file.exists()) file.delete();
			WritableWorkbook wwb = Workbook.createWorkbook(file);
			WritableSheet ws = wwb.createSheet("所有玩家信息", 0);
			ws.setColumnView(1, 15);
			ws.setColumnView(2, 15);
			ws.setColumnView(3, 15);
			ws.setColumnView(5, 15);
			ws.setColumnView(6, 15);
			ws.addCell(new Label(0, 0, "团队"));
			ws.addCell(new Label(1, 0, "人名"));
			ws.addCell(new Label(2, 0, "ID"));
			ws.addCell(new Label(3, 0, "账号"));
			ws.addCell(new Label(4, 0, "返水"));
			ws.addCell(new Label(5, 0, "联系号码"));
			ws.addCell(new Label(6, 0, "密码"));
			int count = 1;
			for(Player player:players){
				ws.addCell(new Label(0, count, player.getTeam()));
				ws.addCell(new Label(1, count, player.getName()));
				ws.addCell(new Label(2, count, player.getId()));
				ws.addCell(new Label(3, count, player.getAccount()));
				ws.addCell(new Label(4, count, player.getInterest()+""));
				ws.addCell(new Label(5, count, player.getTel()));
				ws.addCell(new Label(6, count, player.getPassword()));
				count++;
			}
			wwb.write();
			wwb.close();
			req.setAttribute("success", "导出玩家信息成功！");
		}catch(Exception e){
			System.out.println(e.getMessage());
			req.setAttribute("success", "导出玩家信息失败！");
		}
	}
	private void playerOutThree(HttpServletRequest req){
		try{
			List<Player> players = PlayerDao.getPlayers(req.getParameter("team"));
			File file = new File("C:/excel/"+req.getParameter("team")+"'s players.xls");
			if(file.exists()) file.delete();
			WritableWorkbook wwb = Workbook.createWorkbook(file);
			WritableSheet ws = wwb.createSheet("玩家信息", 0);
			ws.setColumnView(0, 15);
			ws.setColumnView(1, 15);
			ws.addCell(new Label(0, 0, "ID"));
			ws.addCell(new Label(1, 0, "账号"));
			int count = 1;
			for(Player player:players){
				ws.addCell(new Label(0, count, player.getId()));
				ws.addCell(new Label(1, count, player.getAccount()));
				count++;
			}
			wwb.write();
			wwb.close();
			req.setAttribute("success", "导出玩家信息成功！");
		}catch(Exception e){
			System.out.println(e.getMessage());
			req.setAttribute("success", "导出玩家信息失败！");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
