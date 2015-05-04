package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import Model.User;

public class UserDao {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void create(User user ) throws ClassNotFoundException, SQLException{
		Connection c = dataSource.getConnection();
//		insert
	}
	
	public void recieve(User user) throws ClassNotFoundException, SQLException{
		Connection c = dataSource.getConnection();
//		recieve
	}
	
}
