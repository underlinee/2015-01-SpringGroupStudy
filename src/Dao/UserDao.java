package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Model.User;

public class UserDao {
	private ConnectionMaker connectionMaker;
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	public void create(User user ) throws ClassNotFoundException, SQLException{
		Connection c = connectionMaker.makeNewConnection();
//		insert
	}
	
	public void recieve(User user) throws ClassNotFoundException, SQLException{
		Connection c = connectionMaker.makeNewConnection();
//		recieve
	}
	
}
