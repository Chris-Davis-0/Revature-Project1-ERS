package com.revature.chrisdavis.service;

import static org.junit.Assert.assertEquals;
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

import com.revature.chrisdavis.dao.ReimbursementDao;
import com.revature.chrisdavis.dao.ReimbursementStatusDao;
import com.revature.chrisdavis.dao.ReimbursementTypeDao;
import com.revature.chrisdavis.dao.UserDao;
import com.revature.chrisdavis.model.Reimbursement;
import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;



public class ReimbursementServiceTest{
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private ReimbursementDao rDao;
	@Mock private UserDao uDao;
	@Mock private ReimbursementStatusDao rStatusDao;
	@Mock private ReimbursementTypeDao rTypeDao;
	@Mock private Query<User> mockQuery;
	
	List<User> fullUserList;
	List<User> queryUserList;
	List<Reimbursement> fullReimbList;
	List<Reimbursement> queryReimbList;
	List<Reimbursement> emptyQueryList;
	
	@Mock private ReimbursementService rService;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		rDao = spy(new ReimbursementDao(mockHiUtil));
		uDao = spy(new UserDao(mockHiUtil));
		rStatusDao = spy(new ReimbursementStatusDao(mockHiUtil));
		rTypeDao = spy(new ReimbursementTypeDao(mockHiUtil));
		rService = new ReimbursementService(rDao, uDao, rStatusDao);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullUserList = new ArrayList<>();
		queryUserList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		fullReimbList = new ArrayList<>();
		queryReimbList = new ArrayList<>();
		User agent = new User(1, "007", "bondPassword", "James", "Bond","bondjamesbond@mi6.gov", new UserRole(10, "Operative"));
		User admin = new User(3, "Q", "chiefPassword", "Bill", "Nye","q@mi6.gov", new UserRole(12, "Chief"));
		fullReimbList.add(new Reimbursement(1, 12345.67, null, null, "Expense 1 description", null, agent, admin, "Resovler comment", 
				new ReimbursementStatus(2, "Approved"), new ReimbursementType(1, "Travel")));
		fullReimbList.add(new Reimbursement(1, 12345.67, null, null, "Expense 1 description", null, agent, admin, "Resovler comment", 
				new ReimbursementStatus(3, "Denied"), new ReimbursementType(2, "Food")));
		fullUserList.add(new User(1, "007", "bondPassword", "James", "Bond","bondjamesbond@mi6.gov", new UserRole(10, "Operative")));
		fullUserList.add(new User(2, "M", "headPassword", "M", "Just-M","m@mi6.gov", new UserRole(11, "Head")));
		fullUserList.add(new User(3, "Q", "chiefPassword", "Bill", "Nye","q@mi6.gov", new UserRole(12, "Chief")));
		fullUserList.add(new User(4, "$.01", "secretaryPassword", "Pam", "Beasley","hr@mi6.gov", new UserRole(13, "Secretary")));
	}
	
	@Test public void testFindAllReimbursmentsSuccess() { 
		Mockito.doReturn(fullReimbList).when(rDao).selectAll();
		assertEquals(rService.selectAllReimb(), fullReimbList);
	}
	
	@Test public void testSelectReimbursementsByUserNameSuccess() { 
		Mockito.doReturn(fullReimbList).when(rDao).selectByAuthorUserName("007");
		assertEquals(rService.selectAllReimbByUserName("007"), fullReimbList);
	}
	
	@Test public void testSelectReimbursementsByUserNameNotFound() { 
		Mockito.doReturn(emptyQueryList).when(rDao).selectByAuthorUserName("001");
		assertEquals(rService.selectAllReimbByUserName("001"), emptyQueryList);
	}
	
	@Test public void testSelectReimbursementsByUserNameFilteredSuccess() { 
		queryReimbList.add(fullReimbList.get(0));
		Mockito.doReturn(queryReimbList).when(rDao).selectByAuthorUserName("007", "Approved");
		assertEquals(rService.selectAllReimbByUserName("007", "Approved"), queryReimbList);
	}
	
	@Test public void testSelectReimbursementsByUserNameFilteredNotFound() { 
		Mockito.doReturn(emptyQueryList).when(rDao).selectByAuthorUserName("007", "Pending");
		assertEquals(rService.selectAllReimbByUserName("007", "Pending"), emptyQueryList);
	}
	
	@Test public void testSelectReimbursementsBySessionUserNameSuccess() { 
		Mockito.doReturn(fullUserList.get(0)).when(uDao).selectByUserName("007");
		Mockito.doReturn(fullReimbList).when(rDao).selectReimbToProcess(fullUserList.get(0));
		assertEquals(rService.selectReimbToResolveBySessionUser("007"), fullReimbList);
	}
	
	@Test public void testSelectReimbursementsBySessionUserNameNoneFound() { 
		Mockito.doReturn(fullUserList.get(0)).when(uDao).selectByUserName("007");
		Mockito.doReturn(emptyQueryList).when(rDao).selectReimbToProcess(fullUserList.get(0));
		assertEquals(rService.selectReimbToResolveBySessionUser("007"), emptyQueryList);
	}
	
	@Test public void testSelectReimbursementsBySessionUserNameFilterSuccess() { 
		queryReimbList.add(fullReimbList.get(0));
		Mockito.doReturn(fullUserList.get(0)).when(uDao).selectByUserName("007");
		Mockito.doReturn(queryReimbList).when(rDao).selectReimbToProcess(fullUserList.get(0), "Approved");
		assertEquals(rService.selectReimbToResolveBySessionUser("007", "Approved"), queryReimbList);
	}
	
	@Test public void testSelectReimbursementsBySessionUserNameFilterNoneFound() { 
		Mockito.doReturn(fullUserList.get(0)).when(uDao).selectByUserName("007");
		Mockito.doReturn(emptyQueryList).when(rDao).selectReimbToProcess(fullUserList.get(0), "Pending");
		assertEquals(rService.selectReimbToResolveBySessionUser("007", "Pending"), emptyQueryList);
	}		
}
