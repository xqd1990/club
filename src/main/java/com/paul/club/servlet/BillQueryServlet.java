package com.paul.club.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.paul.club.dao.BillDao;
import com.paul.club.dao.ClubDao;
import com.paul.club.dao.ScoreDao;
import com.paul.club.entity.Club;
import com.paul.club.entity.DayBill;
import com.paul.club.entity.Player;

public class BillQueryServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		String flag = req.getParameter("flag");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		if("0".equals(flag)){
			String team = req.getParameter("team");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> dates = dateSplit(start, end);
			pw.print("<tr><th>"+team+"</th>");
			int count = dates.size();
			for(Date date:dates){
				String d = sdf.format(date);
				pw.print("<th>"+d.substring(8,10)+"</th>");
			}
			pw.print("<th style='background-color:#FFE899'>充分小计</th><th style='background-color:#9BE99F'>累计</th></tr>");
			List<String> names = ScoreDao.getNamesByTeam(team);
			int color = 0;
			for(String name:names){
				if(color%2==0)
					pw.print("<tr style='background-color:#ECECEC'><td style='background-color:#FFE899'>"+name+"</td>");
				else
					pw.print("<tr style='background-color:#C9DAE2'><td style='background-color:#FFE899'>"+name+"</td>");
				color++;
				List<DayBill> bills = BillDao.getBillsByName(name, team, start, end);
				int addScore = 0;
				int score_total = 0;
				for(Date date:dates){
					addScore = addScore+ScoreDao.getScore(name, team, sdf.format(date));
					if(bills.size()==0){
						pw.print("<td>"+(0-ScoreDao.getScore(name, team, sdf.format(date)))+"</td>");
					}else{
						int score = 0;
						for(DayBill bill:bills){
							if(bill.getDate().equals(sdf.format(date))){
								int mid = bill.getScore();
								int interest = 0;
								if(mid>0){
									interest = Math.round((float)(mid*(bill.getPlayer().getInterest()+bill.getClub().getExtra())));
								}
								score = score+mid-interest;
								score_total = score_total+mid-interest;
							}
						}
						pw.print("<td>"+(score-ScoreDao.getScore(name, team, sdf.format(date)))+"</td>");
						
					}
				}
				pw.print("<td>"+addScore+"</td><td>"+(score_total-addScore)+"</td>");
				pw.print("</tr>");
				
			}
			if(color%2==0)
				pw.print("<tr style='background-color:#ECECEC'><td id='total' style='background-color:#9BE99F'>累计</td>");
			else
				pw.print("<tr style='background-color:#C9DAE2'><td id='total' style='background-color:#9BE99F'>累计</td>");
			for(int i=0;i<count;i++){
				pw.print("<td></td>");
			}
			pw.print("<td style='background-color:#FFE899'></td><td style='background-color:#FF7A74'></td></tr>");
			pw.flush();
			pw.close();
		}else if("1".equals(flag)){
			String pt = req.getParameter("player");
			int div = pt.indexOf('/');
			String name = pt.substring(0, div);
			String team = pt.substring(div+1);
			queryBillsByName(name, team, start, end);
		}else if("2".equals(flag)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> dates = dateSplit(start, end);
			pw.print("<tr><th>团队</th>");
			for(Date date:dates){
				String d = sdf.format(date);
				pw.print("<th>"+d.substring(8,10)+"</th>");
			}
			pw.print("<th>总计</th></tr>");
			List<String> teams = ScoreDao.getTeams();
			for(String team:teams){
				List<DayBill> bills = BillDao.getBillsByTeam(start, end, team);
				pw.print("<tr>");
				double total = 0;
				pw.print("<td>"+team+"</td>");
				for(Date date:dates){
					String d = sdf.format(date);
					double interest = 0;
					for(DayBill bill:bills){
						if(bill.getDate().equals(d)){
							if(bill.getScore()>0)
								interest = interest+bill.getScore()*(bill.getPlayer().getInterest()-0.05+bill.getClub().getInterest());
							else
								interest = interest+(-bill.getScore())*bill.getClub().getInterest()*bill.getClub().getState();
						}
					}
					total = total + interest;
					pw.print("<td>"+Math.round(interest)+"</td>");
				}
				pw.print("<td>"+Math.round(total)+"</td></tr>");
			}
			pw.print("<tr><td id='itotal'>累计</td>");
			for(Date date:dates){
				pw.print("<td></td>");
			}
			pw.print("<td></td></tr>");
			pw.flush();
			pw.close();
		}else if("3".equals(flag)){
			String team = req.getParameter("team");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> dates = dateSplit(start, end);
			pw.print("<tr><th>俱乐部名</th>");
			for(Date date:dates){
				String d = sdf.format(date);
				pw.print("<th>"+d.substring(8,10)+"日</th>");
			}
			pw.print("<th>小计</th></tr>");
			List<Club> clubs = ClubDao.getClubs();
			for(Club club:clubs){
				pw.print("<tr><td>"+club.getStatement()+"</td>");
				List<DayBill> bills = BillDao.getBillsByClub(start, end, team, club.getStatement());
				for(Date date:dates){
					int count = 0;
					for(DayBill bill:bills){
						if(bill.getDate().equals(sdf.format(date))) count++;
					}
					pw.print("<td>"+count+"</td>");
				}
				pw.print("<td>"+bills.size()+"</td></tr>");
			}
			pw.print("<tr><td id='tot'>合计</td>");
			for(Date date:dates){
				pw.print("<td></td>");
			}
			pw.print("<td></td></tr>");
			pw.flush();
			pw.close();
		}else if("4".equals(flag)){
			pw.print("<tr><th>日期</th><th>club</th><th>桌</th><th>账号</th><th>积分</th><th>提成</th><th>客人水</th><th>小计</th></tr>");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> dates = dateSplit(start, end);
			int count = 0;
			int total = 0;
			double income = 0;
			int last = 0;
			for(Date date:dates){
				List<DayBill> bills = BillDao.getBillsByDate(sdf.format(date));
				count = count + bills.size();
				for(DayBill bill:bills){
					double interest = 0;
					total = total + bill.getScore();
					if(bill.getScore()>0)
						income = income + Math.abs(bill.getScore()*bill.getClub().getInterest());
					else
						income = income + Math.abs(bill.getScore()*bill.getClub().getInterest()*bill.getClub().getState());
					if(bill.getScore()>0){
						interest = bill.getScore()*(bill.getPlayer().getInterest()+bill.getClub().getExtra());
					}
					DecimalFormat df = new DecimalFormat("#.00");
					String point = null;
					if(bill.getScore()>0)
						point = df.format(Math.abs(bill.getScore()*bill.getClub().getInterest()));
					else
						point = df.format(Math.abs(bill.getScore()*bill.getClub().getInterest()*bill.getClub().getState()));
					pw.print("<tr><td>"+sdf.format(date)+"</td><td>"+bill.getClub().getStatement()+"</td><td>"+bill.getDesk()+"</td><td>"+bill.getPlayer().getAccount()+"</td><td>"+bill.getScore()+"</td><td>"+point+"</td><td>"+Math.round(interest)+"</td><td>"+(bill.getScore()-Math.round(interest))+"</td></tr>");
					last = (int) (last + bill.getScore()-Math.round(interest));
				}
			}
			pw.print("<tr><td>记录条数:</td><td>"+count+"</td><td>积分合计:</td><td>"+total+"</td><td>提成合计:</td><td>"+Math.round(income)+"</td><td>小计合计:</td><td>"+last+"</td></tr>");
		}else if("5".equals(flag)){
			String team = req.getParameter("team");
			List<String> names = ScoreDao.getNamesByTeam(team);
			for(String name:names){
				queryBillsByName(name, team, start, end);
			}
		}else if("10".equals(flag)){
			String team = req.getParameter("team");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> dates = dateSplit(start, end);
			pw.print("<tr><th>"+team+"</th>");
			int count = dates.size();
			for(Date date:dates){
				String d = sdf.format(date);
				pw.print("<th>"+d.substring(8,10)+"</th>");
			}
			pw.print("<th>累计</th></tr>");
			List<String> names = ScoreDao.getNamesByTeam(team);
			for(String name:names){
				pw.print("<tr><td>"+name+"</td>");
				List<DayBill> bills = BillDao.getBillsByName(name, team, start, end);
				int score_total = 0;
				for(Date date:dates){
					if(bills.size()==0){
						pw.print("<td>"+0+"</td>");
					}else{
						int score = 0;
						for(DayBill bill:bills){
							if(bill.getDate().equals(sdf.format(date))){
								int mid = bill.getScore();
								int interest = 0;
								if(mid>0){
									interest = Math.round((float)(mid*(bill.getPlayer().getInterest()+bill.getClub().getExtra())));
								}
								score = score+mid-interest;
								score_total = score_total+mid-interest;
							}
						}
						pw.print("<td>"+score+"</td>");
						
					}
				}
				pw.print("<td>"+score_total+"</td>");
				pw.print("</tr>");
				
			}
			pw.print("<tr><td id='total'>累计</td>");
			for(int i=0;i<count;i++){
				pw.print("<td></td>");
			}
			pw.print("<td></td></tr>");
			pw.flush();
			pw.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private void queryBillsByName(String name, String team, String start, String end) throws IOException{
		List<Player> players = BillDao.getPlayersByName(name, team);
		List<DayBill> bills = BillDao.getBillsByName(name, team, start, end);
		File file = new File("C:/excel/"+name+"-"+team+" "+start+"~"+end+".xls");
		if(file.exists()) file.delete();
		WritableWorkbook wwb = Workbook.createWorkbook(file);
		WritableSheet ws = wwb.createSheet("result", 0);
		ws.setColumnView(2, 16);
		ws.setColumnView(3, 18);
		try {
			WritableCellFormat wcf1 = new WritableCellFormat();
			wcf1.setBackground(Colour.RED);
			wcf1.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
			WritableCellFormat wcf2 = new WritableCellFormat();
			wcf2.setBackground(Colour.RED);
			wcf2.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
			WritableCellFormat wcf3 = new WritableCellFormat();
			wcf3.setBackground(Colour.YELLOW);
			wcf3.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
			WritableCellFormat wcf4 = new WritableCellFormat();
			wcf4.setBackground(Colour.YELLOW);
			wcf4.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
			ws.addCell(new Label(0, 0, name,wcf1));
			ws.addCell(new Label(0, 1, "Date",wcf3));
			ws.addCell(new Label(1, 1, "club",wcf3));
			ws.addCell(new Label(2, 1, "桌",wcf3));
			ws.addCell(new Label(3, 1, "ID",wcf3));
			ws.addCell(new Label(4, 1, "积分",wcf3));
			ws.addCell(new Label(5, 1, "水",wcf3));
			ws.addCell(new Label(6, 1, "小计",wcf3));
			int count = 2;
			for(Player player:players){
				ws.addCell(new Label(3, count, player.getAccount()));
				count++;
			}
			ws.addCell(new Label(0, count, "", wcf4));ws.addCell(new Label(1, count, "", wcf4));ws.addCell(new Label(2, count, "", wcf4));ws.addCell(new Label(3, count, "", wcf4));ws.addCell(new Label(4, count, "", wcf4));ws.addCell(new Label(5, count, "", wcf4));ws.addCell(new Label(6, count, "", wcf4));
			count++;
			String date = start;
			int score_total = 0;
			int score = 0;
			for(DayBill bill:bills){
				if(!bill.getDate().equals(date)){
					ws.addCell(new Label(0, count, date.substring(8,10)+"小计", wcf4));ws.addCell(new Label(1, count, "", wcf4));ws.addCell(new Label(2, count, "", wcf4));ws.addCell(new Label(3, count, "", wcf4));ws.addCell(new Label(4, count, "", wcf4));ws.addCell(new Label(5, count, "", wcf4));ws.addCell(new Label(6, count, score+"", wcf4));
					date = bill.getDate();
					score_total = score_total+score;
					score = 0;
					count++;
				}
				ws.addCell(new Label(0, count, bill.getDate().substring(8,10), wcf4));
				ws.addCell(new Label(1, count, bill.getClub().getStatement()));
				ws.addCell(new Label(2, count, bill.getDesk()));
				ws.addCell(new Label(3, count, bill.getPlayer().getAccount()));
				ws.addCell(new Label(4, count, bill.getScore()+""));
				int interest = 0;
				if(bill.getScore()>0){
					interest = Math.round((float)(bill.getScore()*(bill.getPlayer().getInterest()+bill.getClub().getExtra())));
					ws.addCell(new Label(5, count, -interest+""));
				}else{
					ws.addCell(new Label(5, count, "0"));
				}
				int score_day = bill.getScore()-interest;
				ws.addCell(new Label(6, count, score_day+""));
				score = score + score_day;
				count++;
				if(bill==bills.get(bills.size()-1)){
					ws.addCell(new Label(0, count, date.substring(8,10)+"小计", wcf4));ws.addCell(new Label(1, count, "", wcf4));ws.addCell(new Label(2, count, "", wcf4));ws.addCell(new Label(3, count, "", wcf4));ws.addCell(new Label(4, count, "", wcf4));ws.addCell(new Label(5, count, "", wcf4));ws.addCell(new Label(6, count, score+"", wcf4));
					date = bill.getDate();
					score_total = score_total+score;
					score = 0;
					count++;
				}
			}
			ws.addCell(new Label(0, count, "总计",wcf2));ws.addCell(new Label(6, count, score_total+"",wcf2));
			wwb.write();
			wwb.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static List<Date> dateSplit(String start_time, String end_time){
		Date start = null;
		Date end = null;
		try {
			start = new SimpleDateFormat("yyyy-MM-dd").parse(start_time);
			end = new SimpleDateFormat("yyyy-MM-dd").parse(end_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long split = end.getTime() - start.getTime();
		Long step = split/(24 * 60 * 60 * 1000);
		List<Date> dates = new ArrayList<Date>();
		dates.add(start);
		for(int i=1;i<=step;i++){
			dates.add(new Date(dates.get(i-1).getTime()+(24 * 60 * 60 * 1000)));
		}
		return dates;
	}
	public static void main(String[] args) throws ParseException{
		String test = "2015-10-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(test);
		System.out.println(sdf.format(date));
	}
}
