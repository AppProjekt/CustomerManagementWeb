package com.cm.web.beans;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;

import com.cm.ejb.interfaces.CustomerDAO;
import com.cm.ejb.interfaces.MessageSender;
import com.cm.persistence.entities.Customer;

@ApplicationScoped
public class MailSender {

	@EJB
	private MessageSender messageSender;
	
	@EJB
	private CustomerDAO customerDAO;
	
	@Resource(name = "DefaultManagedExecutorService")
	private ManagedExecutorService executor;
	
	public void sendMail(final String message, final String subject, final int recipientId) {
		
		Runnable task = new Runnable() {
			
			@Override
			public void run() {

				Customer recipient = customerDAO.getCustomer(recipientId);
				Future<Boolean> result = messageSender.send(subject, message, recipient);
				
				try {
					result.get();
				}
				catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		executor.submit(task);
	}
	
}
