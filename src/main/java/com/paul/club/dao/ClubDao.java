package com.paul.club.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.paul.club.entity.Club;
import com.paul.club.util.EntityUtil;

public class ClubDao {
	public static List<Club> getClubs(){
		EntityManager em = EntityUtil.getEntityManager();
		List<Club> clubs = em.createQuery("select c from Club c order by c.statement").getResultList();
		em.close();
		return clubs;
	}
	public static void addClub(Club club){
		EntityManager em = EntityUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(club);
		em.getTransaction().commit();
		em.close();
	}
	public static void deleteClub(String id){
		EntityManager em = EntityUtil.getEntityManager();
		Club club = em.find(Club.class, id);
		em.getTransaction().begin();
		em.remove(club);
		em.getTransaction().commit();
		em.close();
	}
	public static void mergeClub(Club club){
		EntityManager em = EntityUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(club);
		em.getTransaction().commit();
		em.close();
	}
}
