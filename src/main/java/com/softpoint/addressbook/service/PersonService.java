package com.softpoint.addressbook.service;

import java.util.List;

import com.softpoint.addressbook.model.Person;


public interface PersonService {
	public boolean insertPerson(Person person);
	
	public boolean updatePerson(Person person);
	
	public List<Person> getAllPerson();
}
