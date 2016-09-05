package com.softpoint.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softpoint.addressbook.dao.SocialAccountDao;
import com.softpoint.addressbook.model.SocialAccount;

@Service
public class SocialAccountServiceImpl implements SocialAccountSevice {

	@Autowired
	private SocialAccountDao socialAccountDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<SocialAccount> getAll() {
		return socialAccountDao.getAll();
	}

}
