package com.revature.chrisdavis.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static Session session;
	public static SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public Session getSession() { 
		if(session == null) {
			session = sf.openSession();
		}
		return session; 
	}
}
