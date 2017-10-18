package com.paul.club.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.paul.club.entity.Player;

import jxl.Sheet;
import jxl.Workbook;

public class EntityUtil {
	
	private static EntityManagerFactory emf;
	
	static{
		emf = Persistence.createEntityManagerFactory("club");
	}
	
	public static EntityManager getEntityManager(){
		return emf.createEntityManager();
	}
	
	public static void main(String[] args) throws Exception{
		EntityManager em = EntityUtil.getEntityManager();
//		File file = new File("C:/excel/test.xls");
//		InputStream is = new FileInputStream(file);
//		Workbook wb = Workbook.getWorkbook(is);
//		Sheet sheet = wb.getSheet(0);
//		em.getTransaction().begin();
//		for(int i=0;i<127;i++){
//			System.out.println(sheet.getCell(0, i).getContents()+"-"+sheet.getCell(1, i).getContents()+"-"+sheet.getCell(2, i).getContents()+"-"+sheet.getCell(3, i).getContents()+"-"+sheet.getCell(4, i).getContents()+"-"+sheet.getCell(5, i).getContents()+"-"+sheet.getCell(6, i).getContents());
//			Player player = new Player();
//			player.setId(sheet.getCell(0, i).getContents());
//			player.setAccount(sheet.getCell(1, i).getContents());
//			player.setInterest(Double.valueOf(sheet.getCell(2, i).getContents()));
//			player.setName(sheet.getCell(3, i).getContents());
//			player.setPassword(sheet.getCell(4, i).getContents());
//			player.setTeam(sheet.getCell(5, i).getContents());
//			player.setTel(sheet.getCell(6, i).getContents());
//			em.persist(player);
//		}
//		em.getTransaction().commit();
	}
}
