package com.softpoint.addressbook.service;

import java.util.List;

import com.softpoint.addressbook.model.Person;
import com.softpoint.addressbook.model.PersonAccount;


public interface PersonAccountService {
	public List<PersonAccount> getPersonAccounts(Person person);
	
	public boolean savePersonAccount(PersonAccount personAccount);

	public boolean updatePersonAccount(PersonAccount personAccount);

	public boolean deletePersonAccount(PersonAccount personAccount);
}
