package com.cm.web.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.cm.ejb.interfaces.CustomerDAO;
import com.cm.persistence.entities.Address;
import com.cm.persistence.entities.Communication;
import com.cm.persistence.entities.Customer;

@ViewScoped
@Named("customers")
public class CustomerBean implements Serializable {
	
	public static final String OUTCOME_INDEX = "/index?faces-redirect=true";
	
	public static final String OUTCOME_UPDATED = "/details?faces-redirect=true&amp;id=%s";
	
	public static final String OUTCOME_DELETED = "/index?faces-redirect=true";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CustomerDAO customerDAO;
	
	private Customer customer;
	
	private int customerId;
	
	private List<AddressBean> addresses = new ArrayList<AddressBean>();
	
	private List<CommunicationBean> communications = new ArrayList<CommunicationBean>();
	
	public void initCustomer() {
		
		if(null != customer) {
			return;
		}
		
		if(customerId > 0) {
			
			customer = customerDAO.getCustomer(customerId);
			
		}
				
		if(customer == null) {
			customer = new Customer();
		}
		
		// Copy the data
		addresses.clear();
		for(Address address : customer.getAddresses()) {
			addresses.add(new AddressBean(address));
		}

		communications.clear();
		for(Communication communication : customer.getCommunications()) 
		{
			communications.add(new CommunicationBean(communication));
		}
	}
	
	/**
	 * Bereitet den Customer-Datensatz für das Speichern vor 
	 */
	private void prepareCustomer() {
		
		// Clear the list of addresses
		customer.getAddresses().clear();
		
		// Add all addresses (again) to the list
		for(AddressBean avm : addresses) {
			customer.getAddresses().add(avm.getAddress());
		}
		
		// Clear the communications
		customer.getCommunications().clear();
		
		// Add all communications (again) to the list
		for(CommunicationBean cvm : communications) {
			customer.getCommunications().add(cvm.getCommunication());
		}
	}
	
	public String create() {
		prepareCustomer();
		
		customerDAO.create(customer);
		
		return OUTCOME_INDEX;
	}

	public String update() {
		prepareCustomer();
		
		customerDAO.update(customer);
		
		return String.format(OUTCOME_UPDATED, customerId);
	}
	
	public String delete() {
		customerDAO.remove(customer.getId());
		
		return OUTCOME_DELETED;
	}
		
	public void addAddress() {
		AddressBean avm = new AddressBean(new Address());
		avm.setEditing(true);
		
		addresses.add(avm);
	}
	
	public void addCommunication() {
		CommunicationBean cvm = new CommunicationBean(new Communication());
		cvm.setEditing(true);
		
		communications.add(cvm);
	}
	
	public void removeAddress(AddressBean toBeRemoved) {
		addresses.remove(toBeRemoved);
	}
	
	public void removeCommunication(CommunicationBean toBeRemoved) {
		communications.remove(toBeRemoved);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<AddressBean> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressBean> addresses) {
		this.addresses = addresses;
	}

	public List<CommunicationBean> getCommunications() {
		return communications;
	}

	public void setCommunications(List<CommunicationBean> communications) {
		this.communications = communications;
	}
	
	public String getBirthdayFormatted() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		if(null == customer) {
			return "---";
		}
		
		return dateFormat.format(customer.getBirthday());
	}
	
	public String getRelationship() {
		
		if(null == customer.getRelationship()) {
			return "Unbekannt";
		}
		
		switch(customer.getRelationship()) {
			case Colleague:
				return "Kollege";
			case Friend:
				return "Freund";
			case Family:
				return "Familie";
			case Job:
				return "Arbeitskollege";
			default:
				return "Unbekannt";
		}
	}
	
}
