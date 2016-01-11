package com.cm.web.beans;

import java.io.Serializable;

import com.cm.persistence.entities.Address;
import com.cm.persistence.enums.Kind;

public class AddressBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Address address;
	
	private boolean isEditing = false;
	
	public AddressBean() {
		// :-)
	}
	
	public AddressBean(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}
	
	public String getAddressKind() {
		if(address.getKind() == null || address.getKind() == Kind.Unknown) {
			return "Unbekannt";
		}
		
		if(address.getKind() == Kind.Business) {
			return "Geschäftsadresse";
		}
		
		return "Privatadresse";
	}
	
	public void toggleEditing() {
		isEditing = !isEditing;
	}
	
}
