package com.paul.club.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.paul.club.entity.Club;
import com.paul.club.entity.DayBill;
import com.paul.club.entity.Player;
import com.paul.club.util.EntityUtil;

public class BillDao {
	public static void addDayBills(List<Object[]> bills){
		EntityManager em = EntityUtil.getEntityManager();
		List<DayBill> dayBills = new ArrayList<DayBill>();
		for(Object[] bill:bills){
			DayBill dayBill = new DayBill();
			dayBill.setDate(bill[0].toString());
			dayBill.setDesk(bill[1].toString());
			dayBill.setScore((Integer)bill[2]);
			Query query = em.createQuery("select p from Player p where p.account=:account");
			query.setParameter("account", bill[4]);
			Player player = (Player) query.getResultList().get(0);
			dayBill.setPlayer(player);
			query = em.createQuery("select c from Club c where c.statement=:statement");
			query.setParameter("statement", bill[3]);
			Club club = (Club) query.getResultList().get(0);
			dayBill.setClub(club);
			dayBills.add(dayBill);
		}
		em.getTransaction().begin();
		for(DayBill bill:dayBills){
			em.persist(bill);
		}
		em.getTransaction().commit();
		em.close();
	}
	public static List<Player> getPlayers(String team, String start, String end){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select p from Player p, DayBill d where p.id=d.player.id and p.team=:team and d.date>=:start and d.date<=:end order by p.name asc");
		query.setParameter("team", team);
		query.setParameter("start", start);
		query.setParameter("end", end);
		List<Player> players = query.getResultList();
		for(Player player:players){
			Set<DayBill> bills = player.getBills();
			for(DayBill bill:bills){
				bill.getDesk();
			}
		}
		em.close();
		return players;
	}
	public static boolean checkBillHasAdded(String check,String date,Integer score){
		boolean flag = false;
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select d from DayBill d where d.score=:score and d.date=:date and d.club.statement=:statement");
		query.setParameter("score", score);
		query.setParameter("date", date);
		query.setParameter("statement", check);
		List<DayBill> result = query.getResultList();
		if(!result.isEmpty()){
			flag = true;
		}
		em.close();
		return flag;
	}
	public static List<DayBill> getBillsByName(String name, String team, String start, String end){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select d from DayBill d where d.date>=:start and d.date<=:end and d.player.name=:name and d.player.team=:team order by d.date, d.player.account");
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("name", name);
		query.setParameter("team", team);
		List<DayBill> bills = query.getResultList();
		em.close();
		return bills;
	}
	public static List<Player> getPlayersByName(String name, String team){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select p from Player p where p.name=:name and p.team=:team order by p.account");
		query.setParameter("name", name);
		query.setParameter("team", team);
		List<Player> players = query.getResultList();
		em.close();
		return players;
	}
	public static List<DayBill> getBillsByTeam(String start, String end, String team){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select d from DayBill d where d.date>=:start and d.date<=:end and d.player.team=:team");
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("team", team);
		List<DayBill> bills = query.getResultList();
		em.close();
		return bills;
	}
	public static List<DayBill> getBillsByClub(String start, String end, String team, String statement){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select d from DayBill d where d.date>=:start and d.date<=:end and d.player.team=:team and d.club.statement=:statement");
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("team", team);
		query.setParameter("statement", statement);
		List<DayBill> bills = query.getResultList();
		em.close();
		return bills;
	}
	public static List<DayBill> getBillsByDate(String date){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select d from DayBill d where d.date=:date order by d.club.statement, d.desk");
		query.setParameter("date", date);
		List<DayBill> bills = query.getResultList();
		em.close();
		return bills;
	}
	public static void deleteBills(String start, String end){
		EntityManager em = EntityUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("delete DayBill d where d.date>=:start and d.date<=:end");
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
}
