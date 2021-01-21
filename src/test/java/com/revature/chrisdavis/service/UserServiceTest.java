package com.revature.chrisdavis.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.chrisdavis.dao.UserDao;
import com.revature.chrisdavis.dao.UserRoleDao;
import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;


public class UserServiceTest {
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private UserDao uDao;
	@Mock private UserRoleDao uRoleDao;
	@Mock private Query<User> mockQuery;
	
	@Mock List<User> fullUserList;
	List<User> queryUserList;
	List<User> emptyQueryList;
	
	@Mock private UserService uService;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		uDao = spy(new UserDao(mockHiUtil));
		uRoleDao = spy (new UserRoleDao(mockHiUtil));
		uService = new UserService(uDao, uRoleDao);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullUserList = new ArrayList<>();
		queryUserList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		fullUserList.add(new User(1, "007", "bondPassword", "James", "Bond","bondjamesbond@mi6.gov", new UserRole(10, "Operative")));
		fullUserList.add(new User(2, "M", "headPassword", "M", "Just-M","m@mi6.gov", new UserRole(11, "Head")));
		fullUserList.add(new User(3, "Q", "chiefPassword", "Bill", "Nye","q@mi6.gov", new UserRole(12, "Chief")));
		fullUserList.add(new User(4, "$.01", "secretaryPassword", "Pam", "Beasley","hr@mi6.gov", new UserRole(13, "Secretary")));
	}
	
	@Test public void testFindAllUsersSuccess() {
		Mockito.doReturn(fullUserList).when(uDao).selectAllUsers();
		assertEquals(uService.getAllUsers(), fullUserList);
	}
	
	@Test public void testSelectUserByUserNameSuccess() {
		Mockito.doReturn(fullUserList.get(1)).when(uDao).selectByUserName("M");
		assertEquals(uService.selectUserByUserName("M"), fullUserList.get(1));
	}
	
	@Test public void testSelectUserByUserNameNoneFound() {
		Mockito.doReturn(null).when(uDao).selectByUserName("006");
		assertEquals(uService.selectUserByUserName("006"), null);
	}
	
	@Test public void testVerifyUserNameTrue() {
		Mockito.doReturn(fullUserList.get(1)).when(uDao).selectByUserName("M");
		assertTrue(uService.verifyUserName("M"));
	}
	
	@Test public void testVerifyUserNameFalse() {
		Mockito.doReturn(null).when(uDao).selectByUserName("N");
		assertFalse(uService.verifyUserName("N"));
	}
	
	@Test public void testVerifyLoginTrue() {
		Mockito.doReturn(fullUserList.get(1)).when(uDao).selectByUsernameAndPassword("M", "headPassword");
		assertTrue(uService.verifyLogin("M", "headPassword"));
	}
	
	@Test public void testVerifyLoginFalse() {
		Mockito.doReturn(null).when(uDao).selectByUsernameAndPassword("M", "footPassword");
		assertFalse(uService.verifyLogin("M", "footPassword"));
	}
	
	@Test public void testSelectUserRoleByUserNameSuccess() {
		Mockito.doReturn(fullUserList.get(1)).when(uDao).selectByUserName("M");
		Mockito.doReturn(fullUserList.get(1).getUserRole().getRole()).when(uRoleDao).findUserRoleById(11);
		assertEquals(uService.getUserRoleByUsername("M"), "Head");
	}
	
	@Test public void testSelectUserRoleByUserNameNoneFound() {
		Mockito.doReturn(null).when(uDao).selectByUserName("Z");
		Mockito.doReturn(null).when(uRoleDao).findUserRoleById(11);
		assertEquals(uService.getUserRoleByUsername("Z"), "Unauthorized");
	}
	
}
