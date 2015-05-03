package Dao;

import java.sql.Connection;

public class NUserDao extends UserDao{

	protected Connection getConnection() {
		return new AConnection();
	}

}
