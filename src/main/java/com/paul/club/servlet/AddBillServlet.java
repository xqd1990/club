package com.paul.club.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.paul.club.dao.BillDao;
import com.paul.club.entity.DayBill;

public class AddBillServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		String date = null;
		String check = null;
		int test = 0;
		try {
			List<FileItem> files = upload.parseRequest(req);
			for(FileItem file:files){
				if(!file.isFormField()){
					date = file.getName().replaceAll(".xls","");
					InputStream is = file.getInputStream();
					Workbook wb = Workbook.getWorkbook(is);
					Sheet sheet = wb.getSheet(0);
					check = sheet.getCell(0,1).getContents().trim();
					test = Integer.valueOf(sheet.getCell(3, 1).getContents().trim());
					if(BillDao.checkBillHasAdded(check, date, test)){
						pw.print("重复导入！");
						pw.flush();
						pw.close();
						return;
					}
					int count = sheet.getRows();
					List<Object[]> bills = new ArrayList<Object[]>();
					for(int i=1;i<count;i++){
						String club_statement = sheet.getCell(0,i).getContents().trim();
						if("".equals(club_statement)) break;
						String desk = sheet.getCell(1, i).getContents().trim();
						String player_name = sheet.getCell(2, i).getContents().trim();
						int score = Integer.valueOf(sheet.getCell(3, i).getContents().trim());
						Object[] bill = new Object[]{date,desk,score,club_statement,player_name};
						bills.add(bill);
					}
					BillDao.addDayBills(bills);
					pw.print("导入成功！");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			pw.print("导入失败！");
		}
		pw.flush();
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
