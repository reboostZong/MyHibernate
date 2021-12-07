package com.zcf;

import com.zcf.pojo.User;
import com.zcf.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

public class HibernateTest {

    @Test
    public void testInit() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() //默认加载resource下的hibernate.cfg.xml, 否则写入配置文件参数
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();

        User user = new User();
        user.setName("zhangsan");
        user.setPwd("123");

        session.save(user);

        tx.commit();
        session.close();

    }

    @Test
    public void testStandardInit() {
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure() //默认加载resource下的hibernate.cfg.xml, 否则写入配置文件参数
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            session = sessionFactory.openSession();

            tx = session.beginTransaction();

            User user = new User();
            user.setName("zhangsan");
            user.setPwd("123");

            session.save(user);

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Test
    public void testTransactionTwo() {
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure() //默认加载resource下的hibernate.cfg.xml, 否则写入配置文件参数
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            session = sessionFactory.openSession();

            tx = session.getTransaction();
            tx.begin();


            User user = new User();
            user.setName("zhangsan");
            user.setPwd("123");

            session.save(user);

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Test
    public void testGet() {
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure() //默认加载resource下的hibernate.cfg.xml, 否则写入配置文件参数
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            session = sessionFactory.openSession();

            tx = session.beginTransaction();

            User user = session.get(User.class, 1L);
            System.out.println(user);

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


}
