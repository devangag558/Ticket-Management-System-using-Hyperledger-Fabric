package org.example.repository;

import org.example.entity.ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ticketRepository extends JpaRepository<ticket, String> {
	
	
	
//    Optional<userCustomer> findByEmail(String email); // To fetch user by email
}
