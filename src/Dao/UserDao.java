package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Model.User;

public class UserDao {
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userMapper = new RowMapper<User>(){
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void create(User user) throws SQLException {
		jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}

	public User recieve(String id) throws SQLException {
		
		return jdbcTemplate.queryForObject("select * from users where id = ?",new Object[]{id}, userMapper);
	}

	public void deleteAll() throws SQLException {
		jdbcTemplate.update("delete from users");
	}

	

	public int getCount() throws SQLException {
		return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);

	}

	public List<User> getAll() {
		return jdbcTemplate.query("select * from users", userMapper);
	}

}
