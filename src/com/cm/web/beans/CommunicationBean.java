package com.cm.web.beans;

import java.io.Serializable;

import com.cm.persistence.entities.Communication;
import com.cm.persistence.enums.Kind;

public class CommunicationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Communication communication;
	
	private boolean isEditing;
	
	public CommunicationBean() {
		// :-)
	}
	
	public CommunicationBean(Communication communication) {
		this.communication = communication;
	}

	public Communication getCommunication() {
		return communication;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}
	
	public String getCommunicationKind() {
		if(communication.getKind() == null || communication.getKind() == Kind.Unknown) {
			return "Unbekannt";
		}
		
		if(communication.getKind() == Kind.Business) {
			return "Geschaeftlich";
		}
		
		return "Privat";
	}
	
	public String getCommunicationType() {
		if(communication.getCommunicationType() == null) {
			
			return "Nicht angegeben";
		}
		
		switch(communication.getCommunicationType()) {
			case Email:
				return "E-Mail";
			case Fax:
				return "Fax";
			case Mobile:
				return "Mobil";
			case Telephone:
				return "Festnetz";
			case Web:
				return "Internetadresse";
			default:
				return "Unbekannt";
		}
	}
	
	public void toggleEditing() {
		isEditing = !isEditing;
	}

}
