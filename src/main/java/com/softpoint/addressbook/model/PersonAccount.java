package com.softpoint.addressbook.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON_ACCCOUNTS")
public class PersonAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "URL")
	private String url;
	@Column(name = "CREATED_AT")
	private LocalDate createdAt;
	@ManyToOne
	@JoinColumn(name = "SOCIAL_AC_ID")
	private SocialAccount socialAccount;
	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	public PersonAccount() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public SocialAccount getSocialAccount() {
		return socialAccount;
	}

	public void setSocialAccount(SocialAccount socialAccount) {
		this.socialAccount = socialAccount;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
