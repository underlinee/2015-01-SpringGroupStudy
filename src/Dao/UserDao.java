package Dao;

import java.util.List;

import javax.sql.DataSource;

import Model.User;

public interface UserDao {

	void setDataSource(DataSource dataSource);

	void create(User user);

	User recieve(String id);

	void update(User user);
	
	void deleteAll();

	int getCount();

	List<User> getAll();

}
