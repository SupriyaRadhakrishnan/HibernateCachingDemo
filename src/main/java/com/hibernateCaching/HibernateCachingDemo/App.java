package com.hibernateCaching.HibernateCachingDemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Alien a = null;
		Configuration cfg = new Configuration().configure().addAnnotatedClass(Alien.class);

		ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = cfg.buildSessionFactory(reg);
		Session session1 = sessionFactory.openSession();
		session1.beginTransaction();

		a = (Alien) session1.get(Alien.class, 101);//query is executed
		System.out.println(a);
		
		//a = (Alien) session2.get(Alien.class, 101); picks data from the first level cache
		session1.getTransaction().commit();
		session1.close();
		
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		a = (Alien) session2.get(Alien.class, 101);
		System.out.println(a);
		session2.getTransaction().commit();
		session2.close();
		
		//Query q = session1.createQuery("from Alien where aid = 101)
		//q.setCacheable(true);
		//a = (Alien)q.uniqueResult(); - To fetch SingleQuery
		
		
	}
}
