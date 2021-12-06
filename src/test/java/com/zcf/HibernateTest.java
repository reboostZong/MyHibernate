package com.zcf;

import com.zcf.pojo.User;
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
}
