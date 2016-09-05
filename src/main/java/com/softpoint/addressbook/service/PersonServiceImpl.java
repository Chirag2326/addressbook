package com.softpoint.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softpoint.addressbook.dao.PersonDao;
import com.softpoint.addressbook.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonDao personDao;

	@Override
	@Transactional
	public boolean insertPerson(Person person) {
		return personDao.insertPerson(person);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> getAllPerson() {
		return personDao.getAllPerson();
	}
	
	@Override
	@Transactional
	public boolean updatePerson(Person person) {
		return personDao.updatePerson(person);
	}
}
