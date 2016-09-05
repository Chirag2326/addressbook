package com.softpoint.addressbook.service;

import com.softpoint.addressbook.model.Address;
import com.softpoint.addressbook.model.Person;

public interface AddressService {
	public boolean insertAddress(Address address);

	public Address getAddress(Person person);
}
