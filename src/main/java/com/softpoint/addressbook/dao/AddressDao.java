package com.softpoint.addressbook.dao;

import com.softpoint.addressbook.model.Address;
import com.softpoint.addressbook.model.Person;

public interface AddressDao {
	public boolean insertAddress(Address address);
	
	public Address getAddress(Person person);
}
