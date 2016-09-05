package com.softpoint.addressbook.dao;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softpoint.addressbook.model.Person;
import com.softpoint.addressbook.model.PersonAccount;


@Repository
public class PersonAccountDaoImpl implements PersonAccountDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PersonAccount> getPersonAccounts(Person person) {
		try {
			List<PersonAccount> accounts = sessionFactory.getCurrentSession().createCriteria(PersonAccount.class)
					.add(Restrictions.eq("person", person)).setFetchMode("socialAccount", FetchMode.EAGER).list();
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean savePersonAccount(PersonAccount personAccount) {
		try {
			sessionFactory.getCurrentSession().save(personAccount);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean updatePersonAccount(PersonAccount personAccount) {
		try {
			sessionFactory.getCurrentSession().update(personAccount);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean deletePersonAccount(PersonAccount personAccount) {
		try {
			sessionFactory.getCurrentSession().delete(personAccount);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
