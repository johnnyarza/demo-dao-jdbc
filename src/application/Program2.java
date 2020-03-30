package application;

import java.util.ArrayList;
import java.util.List;
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
		
		System.out.println("=== TEST2 :update Department ====");
		depDao.update(dep);
		System.out.println("Update! " + dep);
		
		//System.out.println("=== TEST3 :delete Department ====");
		//System.out.print("Insert Department's id to delete: ");
		//int id = sc.nextInt();
		//depDao.deleteById(id);
		//System.out.println("Department Id = " + id + " was deleted!");
		
		System.out.println("=== TEST4 :findById Department ====");
		System.out.print("Insert Department's id to find: ");
		int id = sc.nextInt();	
		System.out.println(depDao.findById(id));
		
		System.out.println("=== TEST5 :findAll Department ====");
		List<Department> list = new ArrayList<>();
		list = depDao.findAll();
		list.forEach(System.out::println);
		
		
		
		
		sc.close();
		
	}

}
