package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Model.User;

public abstract class UserDao {
	private SimpleConnectionMaker simpleConnectionMaker;
	
	public UserDao() {
		simpleConnectionMaker = new SimpleConnectionMaker();
	}
	
	public void create(User user ) throws ClassNotFoundException, SQLException{
		Connection c = simpleConnectionMaker.makeNewConnection();
//		insert
	}
	
	public void recieve(User user) throws ClassNotFoundException, SQLException{
		Connection c = simpleConnectionMaker.makeNewConnection();
//		recieve
	}
	
}
