package Dao;

import java.sql.Connection;

import Model.User;

public abstract class UserDao {
	
	public void create(User user ){
		Connection c = getConnection();
//		insert
	}
	public void recieve(User user){
		Connection c = getConnection();
	}
	
	protected abstract Connection getConnection() ;
	
}
