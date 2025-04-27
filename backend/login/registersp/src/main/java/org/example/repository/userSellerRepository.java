package org.example.repository;

import java.util.List;

import org.example.entity.userSeller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userSellerRepository extends JpaRepository<userSeller, String> {

    List<userSeller> findByLedgerRegisteredIsFalse();
//    Optional<userCustomer> findByEmail(String email); // To fetch user by email
}
