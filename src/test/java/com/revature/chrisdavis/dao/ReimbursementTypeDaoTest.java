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
import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;



public class ReimbursementTypeDaoTest {
	@Mock private HibernateUtil mockHiUtil;
	@Mock private Session mockSession;
	@Mock private Query<ReimbursementType> mockQuery;
	List<ReimbursementType> fullStatusList;
	List<ReimbursementType> queryStatusList;
	List<ReimbursementType> emptyQueryList;
	
	private ReimbursementTypeDao urDao;
	
	
	@Before public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		urDao = new ReimbursementTypeDao(mockHiUtil);
		Mockito.doReturn(mockSession).when(mockHiUtil).getSession();
		fullStatusList = new ArrayList<>();
		queryStatusList = new ArrayList<>();
		emptyQueryList = new ArrayList<>();
		fullStatusList.add(new ReimbursementType(1, "Travel"));
		fullStatusList.add(new ReimbursementType(2, "Food"));
		fullStatusList.add(new ReimbursementType(3, "Lodging"));
		fullStatusList.add(new ReimbursementType(4, "Equipment"));
		fullStatusList.add(new ReimbursementType(5, "Legal"));
		fullStatusList.add(new ReimbursementType(6, "Other"));
	}
	
	@Test public void findAllReimbursementStatusSucess() {
		Mockito.when(mockSession.createQuery("FROM ReimbursementType", ReimbursementType.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(fullStatusList);
		assertEquals(urDao.selectAllReimbursementTypes(), fullStatusList);
	}
	
}
