package com.zcf.test;

import com.zcf.pojo.User;
import com.zcf.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class MyHibernateTest {

    /**
     * new - save - close - update
     */
    @Test
    public void testLifeCycle() {
        Session session = null;
        Transaction tx = null;
        User user = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            user = new User();
            user.setName("lisi");
            user.setPwd("123");

            session.save(user);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }

        user.setName("wangwu");

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            session.update(user);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    /**
     * get/load - clear/evict
     */
    @Test
    public void testtestLifeCycle2() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            User user = session.get(User.class, 10L);
//            User user1 = session.load(User.class, 10L); //懒加载

            System.out.println(user);

            session.clear();// 清除所有
//            session.evict(user);// 清除某个

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void testUpdate() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            // 先查询再更新
            User user = session.get(User.class, 10L);
            user.setName("m-lisi");
            session.update(user);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }


    @Test
    public void testDelete() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            // 先查询再删除
            User user = session.get(User.class, 11L);
            if (user != null) {
                session.delete(user);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
