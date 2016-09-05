package com.softpoint.addressbook.dao;

import java.util.List;

import com.softpoint.addressbook.model.Person;
import com.softpoint.addressbook.model.PersonAccount;


public interface PersonAccountDao {

	public List<PersonAccount> getPersonAccounts(Person person);

	public boolean savePersonAccount(PersonAccount personAccount);

	public boolean updatePersonAccount(PersonAccount personAccount);

	public boolean deletePersonAccount(PersonAccount personAccount);

}
