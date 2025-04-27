package org.example.repository;

import java.util.List;

import org.example.entity.dbTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface transactionRepository extends JpaRepository<dbTransaction, String> {
//    Optional<userCustomer> findByEmail(String email); // To fetch user by email
	List<dbTransaction> findBySourceOrDestination(String source, String destination);
	List<dbTransaction> findBySource(String source);
	List<dbTransaction> findByDestination(String destination);
	
}
