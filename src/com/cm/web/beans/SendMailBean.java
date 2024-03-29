package com.cm.web.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cm.ejb.interfaces.CustomerDAO;
import com.cm.persistence.entities.Customer;

@RequestScoped
@Named("mailSender")
public class SendMailBean {

	private static final String OUTCOME_DETAILS = "/pages/details?faces-redirect=true&amp;id=%s";
	
	@Inject
	private MailSender mailSender;
	
	@EJB
	private CustomerDAO customerDAO;
	
	private int customerId;
	
	private Customer customer;
	
	private String subject;
	
	private String message;
	
	private boolean mailSent;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isMailSent() {
		return mailSent;
	}

	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}

	public void initialize() {
		
		if(customerId > 0) {
			customer = customerDAO.getCustomer(customerId);
		}
		
		if(customer == null) {
			customer = new Customer();
		}
	}
	
	public void send() {
		mailSender.sendMail(message, subject, customerId);		
		mailSent = true;
	}	
}
