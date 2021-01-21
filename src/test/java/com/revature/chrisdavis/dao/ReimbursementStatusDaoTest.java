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

import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;



public class ReimbursementStatusDaoTest {
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private Query<ReimbursementStatus> mockQuery;
	List<ReimbursementStatus> fullStatusList;
	List<ReimbursementStatus> queryStatusList;
	List<ReimbursementStatus> emptyQueryList;
	
	private ReimbursementStatusDao urDao;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		urDao = new ReimbursementStatusDao(mockHiUtil);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullStatusList = new ArrayList<>();
		queryStatusList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		fullStatusList.add(new ReimbursementStatus(1, "Travel"));
		fullStatusList.add(new ReimbursementStatus(2, "Food"));
		fullStatusList.add(new ReimbursementStatus(3, "Lodging"));
		fullStatusList.add(new ReimbursementStatus(4, "Equipment"));
		fullStatusList.add(new ReimbursementStatus(5, "Legal"));
		fullStatusList.add(new ReimbursementStatus(6, "Other"));
	}
	
	@Test public void findAllReimbursementStatusSucess() {
		Mockito.when(mockSession.createQuery("FROM ReimbursementStatus", ReimbursementStatus.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullStatusList);
		assertEquals(urDao.selectAllReimbursementStatus(), fullStatusList);
	}
	
	@Test public void testFindStatusByNameSuccess() {
		queryStatusList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("From ReimbursementStatus where status = 'Travel'", ReimbursementStatus.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(queryStatusList);
		assertEquals(urDao.selectReimbursementStatusByName("Travel"), queryStatusList.get(0));
	}
	
	@Test public void testFindStatusByNoneFound() {
		queryStatusList.add(fullStatusList.get(0));
		Mockito.when(mockSession.createQuery("From ReimbursementStatus where status = 'Blues Traveler'", ReimbursementStatus.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(emptyQueryList);
		assertNotEquals(urDao.selectReimbursementStatusByName("Blues Traveler"), queryStatusList.get(0));
	}

	
}
