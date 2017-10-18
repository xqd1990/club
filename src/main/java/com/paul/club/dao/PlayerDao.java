package com.paul.club.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.paul.club.entity.Player;
import com.paul.club.util.DataStandardInterface;
import com.paul.club.util.EntityUtil;

public class PlayerDao {
	public static List<Player> getPlayers(String team){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select p from Player p where p.team=:team");
		query.setParameter("team", team);
		List<Player> players = query.getResultList();
		em.close();
		return players;
	}
	public static List<Player> getPlayers(int page){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select p from Player p order by p.team,p.name");
		query.setFirstResult((page-1)*DataStandardInterface.PLAYER_PAGE_COUNT);
		query.setMaxResults(DataStandardInterface.PLAYER_PAGE_COUNT);
		List<Player> players = query.getResultList();
		em.close();
		return players;
	}
	public static List<Player> getPlayers(){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select p from Player p order by p.team,p.name asc");
		List<Player> players = query.getResultList();
		em.close();
		return players;
	}
	public static int getPlayerNumber(){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select count(*) from Player p");
		int number = Integer.valueOf(query.getResultList().get(0).toString());
		em.close();
		return number;
	}
	public static List<String> getNamesByTeam(){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select p from Player p order by p.team asc");
		List<Player> players = query.getResultList();
		ArrayList<String> teams = new ArrayList<String>();
		for(Player p:players){
			String target = p.getName()+"/"+p.getTeam();
			if(!teams.contains(target))
				teams.add(target);
		}
		em.close();
		return teams;
	}
	public static void addPlayer(Player player){
		EntityManager em = EntityUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(player);
		em.getTransaction().commit();
		em.close();
	}
	public static void deletePlayer(String id){
		EntityManager em = EntityUtil.getEntityManager();
		Player p = em.find(Player.class, id);
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
		em.close();
	}
	public static void modifyPlayer(Player player){
		EntityManager em = EntityUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(player);
		em.getTransaction().commit();
		em.close();
	}
}
