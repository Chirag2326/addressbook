package com.softpoint.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softpoint.addressbook.dao.PersonAccountDao;
import com.softpoint.addressbook.model.Person;
import com.softpoint.addressbook.model.PersonAccount;

@Service
public class PersonAccountServiceImpl implements PersonAccountService {

	@Autowired
	private PersonAccountDao personAccountDao;

	@Override
	@Transactional(readOnly = true)
	public List<PersonAccount> getPersonAccounts(Person person) {
		return personAccountDao.getPersonAccounts(person);
	}
	
	@Override
	@Transactional
	public boolean savePersonAccount(PersonAccount personAccount) {
		return personAccountDao.savePersonAccount(personAccount);
	}
	
	@Override
	@Transactional
	public boolean updatePersonAccount(PersonAccount personAccount) {
		return personAccountDao.updatePersonAccount(personAccount);
	}
	
	@Override
	@Transactional
	public boolean deletePersonAccount(PersonAccount personAccount) {
		return personAccountDao.deletePersonAccount(personAccount);
	}
}
