package com.paul.club.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.paul.club.entity.Score;
import com.paul.club.util.EntityUtil;

public class ScoreDao {
	public static List<String> getTeams(){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select distinct p.team from Player p");
		List<String> results = query.getResultList();
		em.close();
		return results;
	}
	public static List<String> getNamesByTeam(String team){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select distinct p.name from Player p where p.team=:team order by p.name");
		query.setParameter("team", team);
		List<String> names = query.getResultList();
		em.close();
		return names;
	}
	public static List<Score> getScoresByDate(String startDate, String endDate){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select s from Score s where s.date>=:startDate and s.date<=:endDate");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Score> scores = query.getResultList();
		em.close();
		return scores;
	}
	public static void addScoreRecord(Score score){
		EntityManager em = EntityUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(score);
		em.getTransaction().commit();
		em.close();
	}
	public static void deleteScoreRecord(int id){
		EntityManager em = EntityUtil.getEntityManager();
		Score score = em.find(Score.class, id);
		em.getTransaction().begin();
		em.remove(score);
		em.getTransaction().commit();
		em.close();
	}
	public static List<Score> getScores(String start, String end){
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select s from Score s where s.date>=:start and s.end<=:end");
		query.setParameter("start", start);
		query.setParameter("end", end);
		List<Score> scores = query.getResultList();
		em.close();
		return scores;
	}
	public static int getScore(String name, String team, String date){
		int score = 0;
		EntityManager em = EntityUtil.getEntityManager();
		Query query = em.createQuery("select s from Score s where s.date=:date and s.name=:name and s.team=:team");
		query.setParameter("date", date);
		query.setParameter("name", name);
		query.setParameter("team", team);
		List<Score> scores = query.getResultList();
		for(Score s:scores){
			score = score + s.getScore();
		}
		em.close();
		return score;
	}
}
