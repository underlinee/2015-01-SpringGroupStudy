import java.sql.SQLException;

import org.junit.Test;

import Dao.DaoFactory;
import Dao.UserDao;
import Model.User;


public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		UserDao userDao = new DaoFactory().userDao();
		User user = new User();
		
		
		userDao.create(user);
		userDao.recieve(user);
	}


}
