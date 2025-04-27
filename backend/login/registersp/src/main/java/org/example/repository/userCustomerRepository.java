package org.example.repository;

import org.example.entity.userCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface userCustomerRepository extends JpaRepository<userCustomer, String> {
//    Optional<userCustomer> findByEmail(String email); // To fetch user by email
//	@Query("SELECT c FROM Customer c WHERE c.ledgerRegistered = false")
    List<userCustomer> findByLedgerRegisteredIsFalse();
}
