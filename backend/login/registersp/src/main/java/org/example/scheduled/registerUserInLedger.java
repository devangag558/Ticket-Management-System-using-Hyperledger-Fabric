package org.example.scheduled;

import java.util.List;

import org.example.entity.userCustomer;
import org.example.entity.userSeller;
import org.example.repository.userCustomerRepository;
import org.example.repository.userSellerRepository;
import org.example.service.ledgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class registerUserInLedger {
	

	@Autowired
    private userCustomerRepository userCustomerRepository;
	

	@Autowired
    private userSellerRepository userSellerRepository;
	
	@Autowired
	private ledgerService ledgerService;
	
	
	
	@Scheduled(fixedRate = 300000)
	public void registerOnLedger() {
		
		List<userCustomer> customerList = userCustomerRepository.findByLedgerRegisteredIsFalse();
		
		System.out.println(customerList.isEmpty());
		
		for(userCustomer i : customerList) {
			
			System.out.println("Registering the user with email - "+i.getEmail());
			
			if (!i.isCaRegistered()){
				i.setCaRegistered(ledgerService.registerNewCustomer(i.getEmail(), i.getHash(), i.getName(), i.getMobileNumber()));
			}
			if(i.isVisibility()) {
	        	i.setLedgerRegistered(ledgerService.putOnLedgerNew(i.getEmail(), i.getHash(), i.getName(), i.getAddress(),i.getCity(),i.getGender(),i.getAge()));
	        }
	        else {
	        	i.setLedgerRegistered(ledgerService.putOnLedgerNew(i.getEmail(), i.getHash(), "Anonymous", "Mars","Mars","Anonymous",-1));
	        }
//			i.setCaRegistered(ledgerService.putOnLedger(i.getEmail(), i.getHash(), i.getName(), i.getMobileNumber()));
	        
			userCustomerRepository.saveAndFlush(i);
		}
		
	}
	
	@Scheduled(fixedRate = 900000)
	public void registerOnLedgerSeller() {
		
		List<userSeller> sellerList = userSellerRepository.findByLedgerRegisteredIsFalse();
		
		System.out.println(sellerList.isEmpty());
		
		for(userSeller i : sellerList) {
			
			System.out.println("Registering the user with email - "+i.getEmail());
			
			if (!i.isCaRegistered()){
				i.setCaRegistered(ledgerService.registerNewSeller(i.getEmail(), i.getHashPassword(), i.getName(), i.getMobileNumber()));
			}
			i.setLedgerRegistered(ledgerService.putOnLedgerSellerNew(i.getEmail(), i.getHashPassword(), i.getName(), i.getGstNumber(),i.getAddress(),i.getCity()));
	        
//			i.setCaRegistered(ledgerService.putOnLedger(i.getEmail(), i.getHash(), i.getName(), i.getMobileNumber()));
	        
			userSellerRepository.saveAndFlush(i);
		}
		
	}
}
