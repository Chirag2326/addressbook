package com.softpoint.addressbook.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softpoint.addressbook.model.SocialAccount;

@Repository
public class SocialAccountDaoImpl implements SocialAccountDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SocialAccount> getAll() {
		try {
			List<SocialAccount> accounts = sessionFactory.getCurrentSession().createCriteria(SocialAccount.class)
					.list();
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
