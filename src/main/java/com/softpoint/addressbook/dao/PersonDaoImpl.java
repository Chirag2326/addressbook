package com.softpoint.addressbook.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softpoint.addressbook.model.Person;

@Repository
public class PersonDaoImpl implements PersonDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean insertPerson(Person person) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(person);
		//	session.save(person.getAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Person> getAllPerson() {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Person> list = session.createQuery("from Person").list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updatePerson(Person person) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(person);
		//	session.save(person.getAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	}

}
