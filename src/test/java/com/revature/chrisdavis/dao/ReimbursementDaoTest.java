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

import com.revature.chrisdavis.model.Reimbursement;
import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;



public class ReimbursementDaoTest {
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private Query<Reimbursement> mockQuery;
	List<Reimbursement> fullStatusList;
	List<Reimbursement> queryReimbList;
	List<Reimbursement> emptyQueryList;
	List<User> userList;
	
	private ReimbursementDao urDao;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		urDao = new ReimbursementDao(mockHiUtil);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullStatusList = new ArrayList<>();
		queryReimbList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		User agent = new User(1, "007", "bondPassword", "James", "Bond","bondjamesbond@mi6.gov", new UserRole(10, "Operative"));
		User admin = new User(3, "Q", "chiefPassword", "Bill", "Nye","q@mi6.gov", new UserRole(12, "Chief"));
		User roleless = new User(5, "Spy", "itsasecret", "Un", "Known","???@mi6.gov", null);
		fullStatusList.add(new Reimbursement(1, 12345.67, null, null, "Expense 1 description", null, agent, admin, "Resovler comment", 
				new ReimbursementStatus(2, "Approved"), new ReimbursementType(1, "Travel")));
		fullStatusList.add(new Reimbursement(1, 12345.67, null, null, "Expense 1 description", null, agent, admin, "Resovler comment", 
				new ReimbursementStatus(3, "Denied"), new ReimbursementType(2, "Food")));
		userList = new ArrayList<>();
		userList.add(agent);
		userList.add(admin);
		userList.add(roleless);
	}
	
	@Test public void findAllReimbursementSuccess() {
		Mockito.when(mockSession.createQuery("FROM Reimbursement", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullStatusList);
		assertEquals(urDao.selectAll(), fullStatusList);
	}
	
	@Test public void testFindReimbByAuthorNameSuccess() {
		queryReimbList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("FROM Reimbursement where userAuthor.username = '007'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryReimbList);
		assertEquals(urDao.selectByAuthorUserName("007"), queryReimbList);
	}
	
	@Test public void testFindReimbByAuthorNameNoneFound() {
		Mockito.when(mockSession.createQuery("FROM Reimbursement where userAuthor.username = '070'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertEquals(urDao.selectByAuthorUserName("070"), emptyQueryList);
	}
	
	@Test public void testFindReimbByTypeSuccess() {
		queryReimbList.add(fullStatusList.get(1));
		Mockito.when(mockSession.createQuery("FROM Reimbursement where ersType.type = 'Food'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryReimbList);
		assertEquals(urDao.selectByReimbType("Food"), queryReimbList);
	}
	
	@Test public void testFindReimbByTypeNoneFound() {
		Mockito.when(mockSession.createQuery("FROM Reimbursement where ersType.type = 'Legal'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertNotEquals(urDao.selectByReimbType("Legal"), null);
	}
	
	@Test public void testFindReimbByStatusSuccess() {
		queryReimbList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("FROM Reimbursement where ersStatus.status = 'Approved'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryReimbList);
		assertEquals(urDao.selectByReimbStatus("Approved"), queryReimbList);
	}
	
	@Test public void testFindReimbByStatusNoneFound() {
		queryReimbList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("FROM Reimbursement where ersStatus.status = 'Pending'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertNotEquals(urDao.selectByReimbStatus("Pending"), null);
	}

	@Test public void testFindReimbByUserToProcessSuccess() {
		Mockito.when(mockSession.createQuery("FROM Reimbursement where userAuthor.userRole.id < '12' and userAuthor.id != '3'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullStatusList);
		assertEquals(urDao.selectReimbToProcess(userList.get(1)), fullStatusList);
	}
	
	@Test public void testFindReimbByUserToProcessNoRole() {
		Mockito.when(mockSession.createQuery("FROM Reimbursement where userAuthor.userRole.id < '-1' and userAuthor.id != '5'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertNotEquals(urDao.selectReimbToProcess(userList.get(2)), fullStatusList);
	}
	
	@Test public void testFindReimbByUserToProcessWithFilterSuccess() {
		queryReimbList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("FROM Reimbursement where userAuthor.userRole.id < '12' and userAuthor.id != '3' and ersStatus.status = 'Approved'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullStatusList);
		assertEquals(urDao.selectReimbToProcess(userList.get(1), "Approved"), fullStatusList);
	}
	
	@Test public void testFindReimbByUserToProcessWithFilterNoneFound() {
		queryReimbList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("FROM Reimbursement where userAuthor.userRole.id < '12' and userAuthor.id != '3' and ersStatus.status = 'Pending'", Reimbursement.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertNotEquals(urDao.selectReimbToProcess(userList.get(1), "Pending"), fullStatusList);
	}
}



