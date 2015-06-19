package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import Model.User;

public interface UserDao {
	
	RowMapper<User> userMapper = new RowMapper<User>(){
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};
	public void setDataSource(DataSource dataSource);

	public void create(User user);

	public User recieve(String id);

	public void deleteAll();

	

	public int getCount();

	public List<User> getAll();


}
