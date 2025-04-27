package org.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.example.service.*;
import org.example.util.*;
import org.hibernate.validator.constraints.Range;
import org.example.dto.Token;
import org.example.dto.customerUpdateDTO;
import org.example.dto.sellerUpdateDTO;
import org.example.dto.ticketDTO;
import org.example.dto.userCustomerDTO;
import org.example.dto.userRequestDTO;
import org.example.dto.userSellerDTO;
import org.example.dto.vehicleDTO;
import org.example.entity.*;
import org.example.ledger.dto.customerData;
//import org.example.util.*;
import org.example.ledger.dto.sellerData;
import org.example.ledger.dto.ticketJson;
import org.example.ledger.dto.vehicleJson;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class signUpController {
	
	
	@Autowired
    private applicationService applicationService;

	
	@SuppressWarnings("unused")
	@Autowired
    private JwtUtil jwtUtil;
   
	
	@PostMapping("/customer/signup")
    public ResponseEntity<?> registerCustomer(@RequestBody userCustomerDTO user) {
        try {
            userCustomer savedUser = applicationService.registerNewCustomer(user.toEntity());
            return ResponseEntity.ok(Map.of("message", "Customer registered successfully", "email", savedUser.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    

    @PostMapping("/seller/signup")
    public ResponseEntity<?> registerSeller(@RequestBody userSellerDTO user) {
        try {
            userSeller savedUser = applicationService.registerNewSeller(user.toEntity());
            return ResponseEntity.ok(Map.of("message", "Seller registered successfully", "email", savedUser.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
    
    
    @PostMapping("/customer/get")
    public ResponseEntity<?> getCustomer(@RequestBody Token token) {
        try {
            customerData savedUser = applicationService.getCustomer(token.getValue());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/seller/get")
    public ResponseEntity<?> getSeller(@RequestBody Token token) {
        try {
            sellerData savedUser = applicationService.getSeller(token.getValue());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
    @PostMapping("/customer/rateseller")
    public ResponseEntity<?> rateSeller(@RequestParam String sellerId, @RequestParam @Range(min = 0, max = 5) float rating) {
        try {
            boolean status = applicationService.rateSeller(sellerId,rating);
            if (status) {
                return ResponseEntity.ok(Map.of("message", "Seller rated successfully"));
            }

            return ResponseEntity.badRequest().body(Map.of("message", "Seller can't be rated right now please try again"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
    @PostMapping("/customer/login")
    public ResponseEntity<?> loginCustomer(@RequestBody userRequestDTO user) {
        try {
            Optional<String> token = applicationService.authenticateCustomer(user.getEmail(),user.getPassword());
            if (token.isPresent()) {
                return ResponseEntity.ok(Map.of("value", token.get()));
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/seller/login")
    public ResponseEntity<?> loginSeller(@RequestBody userRequestDTO user) {
        try {
        	Optional<String> token = applicationService.authenticateSeller(user.getEmail(),user.getPassword());
            if (token.isPresent()) {
                return ResponseEntity.ok(Map.of("value", token.get()));
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/customer/update")
    public ResponseEntity<?> updateCustomerDetails(@RequestBody customerUpdateDTO customerDTO) {
        try {
            boolean updated = applicationService.updateCustomerDetails(customerDTO.toEntity(),customerDTO.getValue());
            if (updated) {
                return ResponseEntity.ok("Customer details updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Customer update failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while updating customer: " + e.getMessage());
        }
    }
    
    
    
    @PutMapping("/seller/update")
    public ResponseEntity<?> updateSellerDetails(@RequestBody sellerUpdateDTO sellerDTO) {
        try {
            boolean updated = applicationService.updateSellerDetails(sellerDTO.toEntity(), sellerDTO.getValue());
            if (updated) {
                return ResponseEntity.ok("Customer details updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Seller update failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while updating seller: " + e.getMessage());
        }
    }
    
    @PostMapping("/customer/addBalance")
    public ResponseEntity<?> addBalanceCustomer(@RequestParam String token, @RequestParam float amount) {
        try {
            boolean updated = applicationService.addBalanceCustomer(token, amount);
            if (updated) {
                return ResponseEntity.ok("Customer balance updated successfully");
            }
            return ResponseEntity.badRequest().body("Customer not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/seller/addBalance")
    public ResponseEntity<?> addBalanceSeller(@RequestParam String token, @RequestParam float amount) {
        try {
            boolean updated = applicationService.addBalanceSeller(token, amount);
            if (updated) {
                return ResponseEntity.ok("Seller balance updated successfully");
            }
            return ResponseEntity.badRequest().body("Seller not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    @PostMapping("/customer/balance")
    public ResponseEntity<?> fetchBalanceCustomer(@RequestParam String token) {
        try {
            float balance = applicationService.fetchBalanceCustomer(token);

            return ResponseEntity.ok().body(Map.of("value",balance));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/seller/balance")
    public ResponseEntity<?> fetchBalanceSeller(@RequestParam String token) {
        try {
            float balance = applicationService.fetchBalanceSeller(token);
            return ResponseEntity.ok().body(Map.of("value",balance));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/seller/addvehicle")
    public ResponseEntity<?> addVehicle(@RequestBody vehicleDTO vehicle, @RequestParam String value) {
        try {
            vehicle v = applicationService.addVehicle(vehicle.toEntity(), value);
            if (!v.getVehicleId().isEmpty()) {
                return ResponseEntity.ok().body(Map.of("vehicleId",v.getVehicleId()));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Can't add vehicle right now please try again later"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/vehicles")
    public ResponseEntity<?> getAllVehicles() {
        try {

            List<vehicleJson> vehicleList = applicationService.getAllVehicles();
            return ResponseEntity.ok(vehicleList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
    @PostMapping("/customer/book")
    public ResponseEntity<?> bookTicket(@RequestBody ticketDTO ticket,@RequestParam String token ) {
        try {

            ticketJson bookingConfirmation = applicationService.bookTicket(ticket,token);
            return ResponseEntity.ok(bookingConfirmation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}