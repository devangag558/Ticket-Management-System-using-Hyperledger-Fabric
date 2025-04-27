package org.example.repository;

//import org.example.entity.userSeller;
import org.example.entity.vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface vehicleRepository extends JpaRepository<vehicle, String> {
//    Optional<userCustomer> findByEmail(String email); // To fetch user by email
}
