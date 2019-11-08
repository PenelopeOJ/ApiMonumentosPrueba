package com.turismo.apimonumentos.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DatabaseConnector {
	
	public static Session getSession() {
		
		Session session=null;
		StandardServiceRegistry registry=
				new StandardServiceRegistryBuilder()
				.configure()
				.build();
		
		SessionFactory sessionF=
				new MetadataSources(registry)
				.buildMetadata()
				.buildSessionFactory();
		
		session=sessionF.openSession();
		System.out.println("Se creo la sesion Hibernate");
		
		return session;
	}
	
	public static void closeSession(Session session) {
		if (session!=null) {
			session.close();
			System.out.println("Se cerro la sesion Hibernate");
		}
		
	}
	
}
