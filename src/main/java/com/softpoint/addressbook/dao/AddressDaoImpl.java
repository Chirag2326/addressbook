package com.softpoint.addressbook.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softpoint.addressbook.model.Address;
import com.softpoint.addressbook.model.Person;

@Repository
public class AddressDaoImpl implements AddressDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean insertAddress(Address address) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(address);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Address getAddress(Person person) {
		try {
			//hjgmnugj
			Session session = sessionFactory.getCurrentSession();
			Address address = (Address) session.createQuery("from Address where person = :person")
					.setParameter("person", person).uniqueResult();
			return address;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
