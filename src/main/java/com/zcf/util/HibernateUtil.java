package com.zcf.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    static {
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static Session getSession() {
        Session session = threadLocal.get();
        if (session != null) {
            return session;
        }

        // create
        if (registry == null) {
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
        }

        if (sessionFactory == null) {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        }

        session = sessionFactory.openSession();
        threadLocal.set(session);
        return session;
    }

    public static void closeSession() {
        Session session = threadLocal.get();
        if (session != null && session.isOpen()) {
            session.close();
            threadLocal.set(null);
        }
    }
}
