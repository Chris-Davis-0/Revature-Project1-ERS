package com.revature.chrisdavis.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;



public class UserDaoTest {
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private Query<User> mockQuery;
	List<User> fullUserList;
	List<User> queryUserList;
	List<User> emptyQueryList;
	
	private UserDao uDao;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		uDao = new UserDao(mockHiUtil);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullUserList = new ArrayList<>();
		queryUserList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		UserRole operative = new UserRole(10, "Operative");
		fullUserList.add(new User(1, "007", "bondPassword", "James", "Bond","bondjamesbond@mi6.gov", new UserRole(10, "Operative")));
		fullUserList.add(new User(2, "M", "headPassword", "M", "Just-M","m@mi6.gov", new UserRole(11, "Head")));
		fullUserList.add(new User(3, "Q", "chiefPassword", "Bill", "Nye","q@mi6.gov", new UserRole(12, "Chief")));
		fullUserList.add(new User(4, "$.01", "secretaryPassword", "Pam", "Beasley","hr@mi6.gov", new UserRole(13, "Secretary")));
	}
	
	@Test public void testFindByUsernameSuccess() {
		queryUserList.add(fullUserList.get(2));
		Mockito.when(mockSession.createQuery("FROM User WHERE username = 'Q'", User.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryUserList);
		assertEquals(uDao.selectByUserName("Q"), queryUserList.get(0));
	}
	
	@Test public void testFindByUsernameFailure() {
		queryUserList.add(fullUserList.get(2));
		Mockito.when(mockSession.createQuery("FROM User WHERE username = 'G'", User.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertEquals(uDao.selectByUserName("G"), null);
	}
	
	@Test public void testFindAllUsersSuccess() {
		Mockito.when(mockSession.createQuery("FROM User", User.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullUserList);
		assertEquals(mockSession.createQuery("FROM User", User.class).list(), fullUserList);
	}
	
	@Test public void testFindByUsernameAndPasswordSuccess() {
		queryUserList.add(fullUserList.get(2));
		Mockito.when(mockSession.createQuery("FROM User WHERE username = 'Q' AND password = 'chiefPassword'", User.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryUserList);
		assertEquals(uDao.selectByUsernameAndPassword("Q", "chiefPassword"), queryUserList.get(0));
	}
	
	@Test public void testFindByUsernameAndPasswordFailure() {
		queryUserList.add(fullUserList.get(2));
		Mockito.when(mockSession.createQuery("FROM User WHERE username = 'Q' AND password = 'badpassword'", User.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertNotEquals(uDao.selectByUsernameAndPassword("Q", "badpassword"), queryUserList.get(0));
	}
	 
	
	
}
