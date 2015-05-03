package Dao;

import java.sql.Connection;

public class DUserDao extends UserDao{

	protected Connection getConnection() {
		return new BConnection();
	}

}
