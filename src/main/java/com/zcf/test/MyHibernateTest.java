package com.zcf.test;

import com.zcf.pojo.*;
import com.zcf.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;

import java.util.EnumSet;

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

    /**
     * 通过代码创建表
     */
    @Test
    public void testCreateDB() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
    }

    @Test
    public void testSingleMangToOne() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Grade grade = new Grade();
            grade.setName("junior");

            Grade grade1 = new Grade();
            grade1.setName("senior");

//            session.save(grade);
//            session.save(grade1);

            Student student = new Student();
            student.setName("zhangsan");
//            student.setGrade(grade);

            Student student1 = new Student();
            student1.setName("lisi");
//            student1.setGrade(grade);

            Student student2 = new Student();
            student2.setName("wangwu");
//            student2.setGrade(grade1);

            session.save(student);
            session.save(student1);
            session.save(student2);

            session.save(grade);
            session.save(grade1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }


    @Test
    public void testSingleOneToMany() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Grade grade = new Grade();
            grade.setName("junior");

            Grade grade1 = new Grade();
            grade1.setName("senior");


            Student student = new Student();
            student.setName("zhangsan");

            Student student1 = new Student();
            student1.setName("lisi");

            Student student2 = new Student();
            student2.setName("wangwu");

            grade.getStudents().add(student);
            grade.getStudents().add(student1);
            grade1.getStudents().add(student2);

            session.save(grade);
            session.save(grade1);

            session.save(student);
            session.save(student1);
            session.save(student2);

//            session.save(grade);
//            session.save(grade1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void testDoubleOneToMany() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Grade grade = new Grade();
            grade.setName("junior");

            Grade grade1 = new Grade();
            grade1.setName("senior");


            Student student = new Student();
            student.setName("zhangsan");

            Student student1 = new Student();
            student1.setName("lisi");

            Student student2 = new Student();
            student2.setName("wangwu");

//            grade.getStudents().add(student);
//            grade.getStudents().add(student1);
//            grade1.getStudents().add(student2);
            student.setGrade(grade);
            student1.setGrade(grade);
            student2.setGrade(grade1);

            session.save(grade);
            session.save(grade1);

            session.save(student);
            session.save(student1);
            session.save(student2);

//            session.save(grade);
//            session.save(grade1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void testCascadeSave() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Grade grade = new Grade();
            grade.setName("junior");

            Grade grade1 = new Grade();
            grade1.setName("senior");


            Student student = new Student();
            student.setName("zhangsan");

            Student student1 = new Student();
            student1.setName("lisi");

            Student student2 = new Student();
            student2.setName("wangwu");

//            grade.getStudents().add(student);
//            grade.getStudents().add(student1);
//            grade1.getStudents().add(student2);
            student.setGrade(grade);
            student1.setGrade(grade);
            student2.setGrade(grade1);

            session.save(student);
            session.save(student1);
            session.save(student2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }


    @Test
    public void testSingleOneToOne() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            IDCard id1 = new IDCard();
            id1.setCode("110");

            IDCard id2 =new IDCard();
            id2.setCode("111");

            Person p1 = new Person();
            p1.setName("zhangsan");
            p1.setIdCard(id1);

            Person p2 = new Person();
            p2.setName("lisi");
            p2.setIdCard(id2);

            session.save(id1);
            session.save(id2);
            session.save(p1);
            session.save(p2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void testDoubleOneToOne() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            IDCard idCard = session.get(IDCard.class, 1L);
            System.out.println("idcard number:" + idCard.getCode() + ", person:" + idCard.getPerson().getName());

            Person person = session.get(Person.class, 2L);
            System.out.println("person:" + person.getName() + ", idcard number:"+ person.getIdCard().getCode());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
