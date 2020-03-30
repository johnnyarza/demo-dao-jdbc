package application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		//System.out.println("=== TEST 1: insert Department ====");
		Department dep =new Department(9, "Games");
		//depDao.insert(dep);
		//System.out.println("Inserted new Department. New Department Id = " + dep.getId());
		
		System.out.println("=== TEST :update Department ====");
		depDao.update(dep);
		System.out.println("Update! " + dep);
		
		
		sc.close();
		
	}

}
