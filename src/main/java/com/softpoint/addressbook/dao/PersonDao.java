package com.softpoint.addressbook.dao;

import java.util.List;

import com.softpoint.addressbook.model.Person;

public interface PersonDao {
	public boolean insertPerson(Person person);
	
	public List<Person> getAllPerson();

	public boolean updatePerson(Person person);
}
