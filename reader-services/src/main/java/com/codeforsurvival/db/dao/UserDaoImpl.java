package com.codeforsurvival.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codeforsurvival.db.entity.User;

@Repository
class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public User addUser(User user) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (null != (Long) user.getId()) {
				session.saveOrUpdate(user);
			} else {
				Long id = (Long) session.save(user);
				user.setId(id);
			}
			tx.commit();
			return user;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public List<User> getAllUsers() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(User.class);
			cr.setProjection(
					Projections
							.projectionList()
							.add(Projections.property("address"), "address")
							.add(Projections.property("age"), "age")
							.add(Projections.property("enabled"), "enabled")
							.add(Projections.property("id"), "id")
							.add(Projections.property("firstName"), "firstName")
							.add(Projections.property("lastName"), "lastName")
							.add(Projections.property("username"), "username")
							.add(Projections.property("userType"), "userType"))
					.setResultTransformer(Transformers.aliasToBean(User.class));
			@SuppressWarnings("unchecked")
			List<User> list = cr.list();
			tx.commit();
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public User getUserById(Long userId) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("id", userId));
			User user = (User) cr.list().get(0);
			tx.commit();
			return user;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public User getUserByUsername(String username) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", username));
			User user = (User) cr.list().get(0);
			tx.commit();
			return user;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public User deleteUserById(Long userId) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, userId);
			session.delete(user);
			tx.commit();
			return user;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public User deleteUser(User user) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
			return user;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
}
