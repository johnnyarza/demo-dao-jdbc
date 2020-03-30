package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO department (department.Name) " + "values (?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());

			int affectedRows = st.executeUpdate();

			if (affectedRows > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error. No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;

		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement("UPDATE department " 
			+ "SET department.Name = ? " 
			+ "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("No rows are updated!");
			}
			conn.commit();
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				throw new DbException("Transaction rolled back! " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DbException ("Error trying rolling back! " + e1.getMessage());
			}
		} 
		finally {
			DB.closeStatement(st);
			try {
				conn.setAutoCommit(true);
			} 
			catch (SQLException e1) {
				throw new DbException ("Error trying to setAutoCommit = True " + e1.getMessage());
			}
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException("Error deleting Department = " + id + " . " + e.getMessage() );
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department dep = instaciateDepartment(rs);
				return dep;		
			}
			return null;		
		} catch (SQLException e){
			throw new DbException("Error during findById, Id = "+ id +" Cause: " +e.getMessage());
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		try { 
			st = conn.prepareStatement(  
					"SELECT * FROM department");
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while (rs.next()) {			
				Department dep = instaciateDepartment(rs);
				list.add(dep);
			}
			return list;
			
		} catch (SQLException e){
			throw new DbException("Error finding departments. Cause: " + e.getMessage());
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}
	
	private Department instaciateDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt(1), rs.getString(2));
		
	}

}
