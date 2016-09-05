package com.softpoint.addressbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softpoint.addressbook.dao.AddressDao;
import com.softpoint.addressbook.model.Address;
import com.softpoint.addressbook.model.Person;


@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Override
	@Transactional
	public boolean insertAddress(Address address) {
		return addressDao.insertAddress(address);
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddress(Person person) {
		return addressDao.getAddress(person);
	}

}
