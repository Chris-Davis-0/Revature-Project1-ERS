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



public class UserRoleDaoTest {
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private Query<UserRole> mockQuery;
	List<UserRole> fullUserRoleList;
	List<UserRole> queryUserRoleList;
	List<UserRole> emptyQueryList;
	
	private UserRoleDao urDao;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		urDao = new UserRoleDao(mockHiUtil);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullUserRoleList = new ArrayList<>();
		queryUserRoleList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		fullUserRoleList.add(new UserRole(1, "Operative"));
		fullUserRoleList.add(new UserRole(2, "Secretary"));
		fullUserRoleList.add(new UserRole(3, "Chief"));
		fullUserRoleList.add(new UserRole(4, "Head"));
	}
	
	@Test public void testFindByRoleNameSuccess() {
		queryUserRoleList.add(fullUserRoleList.get(0));
		Mockito.when(mockSession.createQuery("FROM UserRole WHERE role = 'Operative'", UserRole.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryUserRoleList);
		assertEquals(urDao.findUserRoleByName("Operative"), queryUserRoleList.get(0));
	}
	
	@Test public void testFindByRoleNameNoneFound() {
		Mockito.when(mockSession.createQuery("FROM UserRole WHERE role = 'Foot'", UserRole.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertEquals(urDao.findUserRoleByName("Foot"), null);
	}
	
	@Test public void findAllUserRolesSuccess() {
		Mockito.when(mockSession.createQuery("FROM UserRole", UserRole.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullUserRoleList);
		assertEquals(urDao.selectAllUserRoles(), fullUserRoleList);
	}
	
	@Test public void testFindByRoleIdSuccess() {
		queryUserRoleList.add(fullUserRoleList.get(0));
		Mockito.when(mockSession.createQuery("FROM UserRole WHERE id = '1'", UserRole.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryUserRoleList);
		assertEquals(urDao.findUserRoleById(1), queryUserRoleList.get(0).getRole());
	}
	
	@Test public void testFindByRoleIdNoneFound() {
		queryUserRoleList.add(fullUserRoleList.get(0));
		Mockito.when(mockSession.createQuery("FROM UserRole WHERE id = '-1'", UserRole.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertEquals(urDao.findUserRoleById(-1), "Unauthorized");
	}
	
}
