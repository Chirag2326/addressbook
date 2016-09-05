package com.softpoint.addressbook.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SOCIAL_ACCCOUNTS")
public class SocialAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ACCOUNT_NAME")
	private String accountName;
	@OneToMany(mappedBy = "socialAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PersonAccount> personAccounts;

	public SocialAccount() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public List<PersonAccount> getPersonAccounts() {
		return personAccounts;
	}

	public void setPersonAccounts(List<PersonAccount> personAccounts) {
		this.personAccounts = personAccounts;
	}

}