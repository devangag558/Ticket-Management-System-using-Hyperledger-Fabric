package org.example.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

//import org.apache.commons.lang3.tuple.Pair;
import org.example.dto.ticketDTO;
import org.example.entity.dbTransaction;
import org.example.entity.ticket;
import org.example.entity.userCustomer;
import org.example.entity.userSeller;
import org.example.entity.vehicle;
import org.example.ledger.dto.bookTicketData;
import org.example.ledger.dto.customerData;
import org.example.ledger.dto.sellerData;
import org.example.ledger.dto.ticketJson;
import org.example.ledger.dto.transactionJson;
import org.example.ledger.dto.vehicleJson;
import org.example.repository.*;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class applicationService {

	@Autowired
	private userCustomerRepository userCustomerRepository;


	@Autowired
	private userSellerRepository userSellerRepository;

	@Autowired
	private vehicleRepository vehicleRepository;

	@Autowired
	private ledgerService ledgerService;

	@Autowired
	private transactionRepository transactionRepository;
	
	@Autowired
	private ticketRepository ticketRepository;

	@Autowired
	private JwtUtil jwtUtil;

	boolean txnadminregistered = false;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Transactional
	public userCustomer registerNewCustomer(userCustomer user) throws Exception {
		Optional<userCustomer> opuser = userCustomerRepository.findById(user.getEmail());



		if (!opuser.isPresent())
		{
			if (user.getPassword() == null || user.getPassword().isBlank()) {
				throw new IllegalArgumentException("Password cannot be empty");
			}
			user.setEmail(user.getEmail().toLowerCase().trim()); // Normalize email
			//        user.setId(user.getEmail()); // Compute ID from email
			// Hash the password before saving
			String hashedPassword = passwordEncoder.encode(user.getPassword());
			user.setHash(hashedPassword); // Store hash in DB
			user.setPassword(null); // Ensure plaintext password is not stored
			user.setCaRegistered(ledgerService.registerNewCustomer(user.getEmail(), hashedPassword, user.getName(), user.getMobileNumber()));
			if(user.isVisibility()) {
				user.setLedgerRegistered(ledgerService.putOnLedgerNew(user.getEmail(), hashedPassword, user.getName(), user.getAddress(),user.getCity(),user.getGender(),user.getAge()));
			}
			else {
				user.setLedgerRegistered(ledgerService.putOnLedgerNew(user.getEmail(), hashedPassword, "Anonymous", "Mars","Mars","NA",-1));
			}
			return userCustomerRepository.saveAndFlush(user);
		}
		throw new Exception("User already registered");
	}

	@Transactional
	public userSeller registerNewSeller(userSeller user) throws Exception {
		Optional<userSeller> opuser = userSellerRepository.findById(user.getEmail());



		if (!opuser.isPresent())
		{
			if (user.getPassword() == null || user.getPassword().isBlank()) {

				throw new IllegalArgumentException("Password cannot be empty");
			}
			user.setEmail(user.getEmail().toLowerCase().trim()); // Normalize email
			//        user.setId(user.getEmail()); // Compute ID from email
			// Hash the password before saving
			String hashedPassword = passwordEncoder.encode(user.getPassword());
			user.setHashPassword(hashedPassword); // Store hash in DB
			user.setPassword(null); // Ensure plaintext password is not stored
			user.setCaRegistered(ledgerService.registerNewSeller(user.getEmail(), hashedPassword, user.getName(), user.getMobileNumber()));
			user.setLedgerRegistered(ledgerService.putOnLedgerSellerNew(user.getEmail(), hashedPassword, user.getName(), user.getGstNumber(),user.getAddress(),user.getCity()));
			return userSellerRepository.saveAndFlush(user);
		}
		throw new Exception("User already registered");
	}


	@Transactional
	public sellerData getSeller(String token) throws Exception {

		Optional<userSeller> opuser = userSellerRepository.findById(jwtUtil.extractEmail(token));
		String email = jwtUtil.extractEmail(token);


		if (opuser.isPresent()){


			userSeller user = opuser.get();
			if (!jwtUtil.isTokenExpired(token)){
				String json = ledgerService.getSellerDetailsNew(email);

				sellerData sellerObj;
				try {
					sellerObj = new sellerData(json);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					// TODO Auto-generated catch block
					sellerObj = new sellerData();
				}

				sellerObj.setEmail(email);
				sellerObj.setBalance(user.getBalance());
				sellerObj.setHashPassword(user.getHashPassword());
				sellerObj.setMobileNumber(user.getMobileNumber());

				return sellerObj;
			}
			throw new Exception("password incorrect");
		}
		throw new Exception("User not present");

	}

	@Transactional
	public customerData getCustomer(String token) throws Exception{

		String email = jwtUtil.extractEmail(token);
		Optional<userCustomer> opuser = userCustomerRepository.findById(email);


		if (opuser.isPresent()){

			userCustomer user = opuser.get();
			if (!jwtUtil.isTokenExpired(token)){
				String json = ledgerService.getCustomerDetailsNew(email);

				customerData CustomerObj;
				try {
					CustomerObj = new customerData(json);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					// TODO Auto-generated catch block
					CustomerObj = new customerData();
				}

				CustomerObj.setEmail(email);
				CustomerObj.setBalance(user.getBalance());
				CustomerObj.setMobileNumber(user.getMobileNumber());
				CustomerObj.setHash(user.getHash());
				CustomerObj.setVisibility(user.isVisibility());
				
				return CustomerObj;
			}
			throw new Exception("password incorrect");
		}
		throw new Exception("user not found");
	}
	

	@Transactional
	public boolean rateSeller(String email, float rating ) {
		Optional<userSeller> opuser = userSellerRepository.findById(email);
		if (opuser.isPresent()){

			userSeller user = opuser.get();

			float temprating = user.getRating();
			int tempnumratings = user.getRatingCount();

			float sum = (temprating* tempnumratings) + rating;
			tempnumratings++;
			sum=sum/tempnumratings;

			user.setRatingCount(tempnumratings);
			user.setRating(sum);

			userSellerRepository.saveAndFlush(user);
			return true;
		}
		return false;

	}

	@Transactional
	public boolean updateCustomerDetails(userCustomer user, String token) throws Exception {
		String email = jwtUtil.extractEmail(token);
		Optional<userCustomer> optionalCustomer = userCustomerRepository.findById(email);
		if (optionalCustomer.isPresent()) {
			userCustomer customer = optionalCustomer.get();

			// Verify password before allowing update
			if (jwtUtil.isTokenExpired(token)) {
				throw new Exception("password not correct"); // Password check failed
			}
			System.out.println("coming till here");
			if (user.getName() != null) customer.setName(user.getName());
			if (user.getAddress() != null) customer.setAddress(user.getAddress());
			if (user.getCity() != null) customer.setCity(user.getCity());
			if (user.getGender() != null) customer.setGender(user.getGender());
			if (user.getAge() > 0) customer.setAge(user.getAge());

			boolean ledgerUpdate = ledgerService.updateOnLedger(email, customer.getName(), customer.getAddress(), customer.getCity(),customer.getGender(),customer.getAge());

			userCustomerRepository.saveAndFlush(customer);
			return true && ledgerUpdate;
		}
		throw new Exception("User not found"); // Seller not found
	}

	@Transactional
	public boolean updateSellerDetails(userSeller user, String token) throws Exception {
		String email = jwtUtil.extractEmail(token);
		Optional<userSeller> optionalSeller = userSellerRepository.findById(email);
		if (optionalSeller.isPresent()) {
			userSeller seller = optionalSeller.get();

			// 1. Verify password before allowing update
			if (jwtUtil.isTokenExpired(token)) {
				throw new Exception("password not correct"); // Password check failed
			}
			if (user.getName() != null) seller.setName(user.getName());
			if (user.getAddress() != null) seller.setAddress(user.getAddress());
			if (user.getCity() != null) seller.setCity(user.getCity());
			
			boolean ledgerUpdate = ledgerService.updateOnLedgerSeller(email, seller.getName(), seller.getAddress(), seller.getCity());
			
			
			// 3. Update only if values are provided (not null or 0)
			// Optional: update rating, balance if exposed through DTO

			userSellerRepository.saveAndFlush(seller);
			return true && ledgerUpdate;
		}
		throw new Exception("User not found"); // Seller not found
	}
	
	
	@Transactional
	public vehicle addVehicle(vehicle vehicle, String token) throws Exception {
		String email = jwtUtil.extractEmail(token);
		Optional<userSeller> optionalSeller = userSellerRepository.findById(email);
		if (optionalSeller.isPresent()) {
			userSeller seller = optionalSeller.get();
//			System.out.println("");
			// 1. Verify password before allowing update
			vehicle.setSellerId(email);
			if (jwtUtil.isTokenExpired(token)) {
				throw new Exception("password not correct"); // Password check failed
			}
			vehicleRepository.saveAndFlush(vehicle);
			
			
			boolean ledgerupdated = ledgerService.addvehicle(email, vehicle.getVehicleId(),vehicle.getSource(),vehicle.getDestination(),
					vehicle.getDepartureDate().toString(),vehicle.getDepartureTime().toString(),vehicle.getMode(),String.valueOf(vehicle.getSeatCapacity()), String.valueOf(vehicle.getBasePrice()));
			
			if (!ledgerupdated) {
				throw new Exception("Ledger not updated right now, please try again later");
			}
			
			seller.addVehicle(vehicle.getVehicleId());
			
			userSellerRepository.saveAndFlush(seller);
			
			

			return vehicle ;
		}
		throw new Exception("User not found"); // Seller not found
	}

	@Transactional
	public float getCurrentPrice(String vehicleId) {
		//    	ledgerService.getAllTravelOptions();
		// Check if the seller exists
		Optional<vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
		if (vehicleOpt.isPresent()) {
			vehicle vehicle = vehicleOpt.get();
			float currentPrice = vehicle.getBasePrice();
			int day = LocalDate.now().getDayOfYear();
			if (vehicle.getAvailableSeats()>0 && day<vehicle.getDayOfYear()) {
				String weekday = LocalDate.now().getDayOfWeek().toString();
				int hour = LocalTime.now().getHour();
				float highestPrice = vehicle.getBasePrice()*1.25f;
				//Price Computation
				float occupancyFactor =1+(0.10f* (1-(vehicle.getAvailableSeats()/vehicle.getSeatCapacity())));

				float closenessFactor =1+(0.10f * (1-((LocalDate.now().getDayOfYear()-vehicle.getDayOfYear())/100)));


				currentPrice = vehicle.getBasePrice()* occupancyFactor*closenessFactor;

				if (weekday.equalsIgnoreCase("sunday")||weekday.equalsIgnoreCase("saturday")) {
					currentPrice = 1.02f * currentPrice;
				}
				if((hour>22 || hour<4)) {
					currentPrice = 1.02f * currentPrice;
				}
				if(highestPrice<currentPrice) {
					currentPrice = highestPrice;
				}
			}
			return currentPrice;    
		}
		throw new RuntimeException("Vehicle not found");
	}
	
	@Transactional
	public float getSellerRating(String sellerId) {
		//    	ledgerService.getAllTravelOptions();
		// Check if the seller exists
		Optional<userSeller> sellerOpt = userSellerRepository.findById(sellerId);
		if (sellerOpt.isPresent()) {
			userSeller seller = sellerOpt.get();
			return seller.getRating();
		}
		throw new RuntimeException("Seller not found");
	}
	
	@Transactional
	public float fetchBalanceCustomer(String token) throws Exception {
		String email = jwtUtil.extractEmail(token);
		
		Optional<userCustomer> opuser = userCustomerRepository.findById(email);

		if (opuser.isPresent()&& !jwtUtil.isTokenExpired(token)){
			userCustomer user = opuser.get();
			
			return user.getBalance();
		}

		throw new Exception("Token not valid");
	}
	
	@Transactional
	public float fetchBalanceSeller(String token) throws Exception {
		String email = jwtUtil.extractEmail(token);
		
		Optional<userSeller> opuser = userSellerRepository.findById(email);

		if (opuser.isPresent()&& !jwtUtil.isTokenExpired(token)){
			userSeller user = opuser.get();
			
			return user.getBalance();
		}
		throw new Exception("Token not valid");
		}
	
	@Transactional
	public boolean addBalanceCustomer(String token, float amount) {
		String email = jwtUtil.extractEmail(token);
		
		Optional<userCustomer> opuser = userCustomerRepository.findById(email);

		if (opuser.isPresent()&& !jwtUtil.isTokenExpired(token)){
			userCustomer user = opuser.get();
			dbTransaction dbtxn = new dbTransaction("app", email, amount);

			boolean ledgerUpdated = ledgerService.newaddTransaction(dbtxn.getTxnId(),
					dbtxn.getSource(),
					dbtxn.getDestination(),
					user.getHash(),
					dbtxn.getAmount());

			transactionRepository.saveAndFlush(dbtxn);
			float newBalance = user.getBalance() + amount;
			user.setBalance(newBalance);
			List<String> trans = user.getTransactionList();
			trans.add(dbtxn.getTxnId());
			user.setTransactionList(trans);
//			user.addTransaction(dbtxn.getTxnId());
			userCustomerRepository.saveAndFlush(user);
			return true&&ledgerUpdated;
		}

		if(userCustomerRepository.findById(email).isEmpty()) return false;
		return false;
	}
	@Transactional
	public boolean addBalanceSeller(String token, float amount) {
		String email = jwtUtil.extractEmail(token);
		Optional<userSeller> opuser = userSellerRepository.findById(email);

		if (opuser.isPresent() && !jwtUtil.isTokenExpired(token)){
			userSeller user = opuser.get();
			dbTransaction dbtxn = new dbTransaction("app", email, amount);
			boolean ledgerUpdated = ledgerService.newaddTransaction(dbtxn.getTxnId(),
					dbtxn.getSource(),
					dbtxn.getDestination(),
					user.getHashPassword(),
					dbtxn.getAmount());
			
			transactionRepository.saveAndFlush(dbtxn);

			float newBalance = user.getBalance() + amount;
			user.setBalance(newBalance);
			user.addTransaction(dbtxn.getTxnId());
			userSellerRepository.save(user);
			return true&&ledgerUpdated;
		}

		if(userSellerRepository.findById(email).isEmpty()) return false;

		return false;
	}


	public Optional<String> authenticateCustomer(String email, String password) {
		return userCustomerRepository.findById(email)
				.filter(user -> passwordEncoder.matches(password, user.getHash()))
				.map(user -> jwtUtil.generateToken(email, "CUSTOMER"));  // Assign CUSTOMER role
	}
	
	public Optional<String> authenticateSeller(String email, String password) {
		return userSellerRepository.findById(email)
				.filter(user -> passwordEncoder.matches(password, user.getHashPassword()))
				.map(user -> jwtUtil.generateToken(email, "SELLER"));  // Assign CUSTOMER role
	}

	// Verify token and extract role
	public Optional<String> verifyToken(String token) {
		try {
			if (jwtUtil.isTokenExpired(token)) {
				return Optional.empty();
			}
			return Optional.of(jwtUtil.extractRole(token));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Transactional
	public List<vehicleJson> getAllVehicles() throws Exception{
		
		String resultJson = ledgerService.getAllVehicles();
		
	    ObjectMapper objectMapper = new ObjectMapper();
        List<vehicleJson> vehicleList = objectMapper.readValue(
                resultJson,
                new TypeReference<List<vehicleJson>>() {}
        );
        
        for(vehicleJson v : vehicleList) {
        	try {
	        	v.setSellerRating(getSellerRating(v.getSellerId()));
	        	v.setCurrentPrice(getCurrentPrice(v.getVehicleId()));
	        }
        	catch(Exception e){

	        	v.setSellerRating(0);
	        	v.setCurrentPrice(v.getBasePrice());
        	}
        }
        return vehicleList;
	}

	@Transactional
	public ticketJson bookTicket(ticketDTO ticket, String token) throws Exception {
		int seatCount = ticket.getSeatCount();
		String vehicleId = ticket.getVehicleNumber();
		System.out.println("vehicleid "+vehicleId+" seats "+seatCount);
		
		String email = jwtUtil.extractEmail(token);
		Optional<userCustomer> opuser = userCustomerRepository.findById(email);
		if(opuser.isPresent() && !jwtUtil.isTokenExpired(token)) {
			userCustomer cust = opuser.get();
			System.out.println("Got customer");
			Optional<vehicle> vopt = vehicleRepository.findById(ticket.getVehicleNumber());
			if (vopt.isPresent()) {
				vehicle v = vopt.get();
				System.out.println("got vehicle");
				if(v.getAvailableSeats()>=seatCount) {
					Optional<userSeller> sellerOpt = userSellerRepository.findById(v.getSellerId());
					userSeller seller = sellerOpt.get();
					float currentPrice = getCurrentPrice(vehicleId);
					float totalPrice = currentPrice * seatCount;
					System.out.println("totalPrice calculated"+totalPrice);
					if(cust.getBalance()>=totalPrice) {
						dbTransaction dbtxn = new dbTransaction(cust.getEmail(), v.getSellerId(), totalPrice);
						ticket newTicket = new ticket(vehicleId, email,seatCount,totalPrice,dbtxn.getTxnId(),v.getBasePrice());
						
						System.out.println("transaction +"+dbtxn);
						
						
						System.out.println("TRANSACTION ID = "+dbtxn.getTxnId());
						
						
						bookTicketData btjson = ledgerService.bookTicket(email, newTicket.getTicketId(),
								vehicleId, seatCount, dbtxn.getTxnId(),totalPrice);

						System.out.println(btjson.toString());
						
						cust.setBalance(cust.getBalance()-totalPrice);
						cust.addTransaction(dbtxn.getTxnId());
						
						seller.setBalance(seller.getBalance()+totalPrice);
						seller.addTransaction(dbtxn.getTxnId());
						
						v.setAvailableSeats(v.getAvailableSeats()-seatCount);
						
						
						
						ticketJson tjson = btjson.getTicketData();
						
						System.out.println(tjson.toString());
						
						if (tjson.getTicketId()==null) {
							throw new Exception ("something went wrong,cant book now");
						}
						newTicket.setBookingBlock(tjson.getBookingBlock());
						newTicket.setBookingTime(LocalDateTime.now());
						v.add(newTicket.getTicketId());
						cust.addTicket(newTicket.getTicketId());
						userCustomerRepository.saveAndFlush(cust);
						vehicleRepository.saveAndFlush(v);
						userSellerRepository.saveAndFlush(seller);
						transactionRepository.saveAndFlush(dbtxn);
						ticketRepository.saveAndFlush(newTicket);
						
						return tjson;
					}
					throw new Exception("Not enough balance to book ticket");
				}
				throw new Exception("Requested number of seats not available");
			}
			throw new Exception("vehicle not found");
		}
		throw new Exception("token expired");
	}
	
	
	private transactionJson cancelTicket(String ticketId, String token) {
		
		String email = jwtUtil.extractEmail(token);
		Optional<userCustomer> opuser = userCustomerRepository.findById(email);
		
		if(opuser.isPresent() && !jwtUtil.isTokenExpired(token)) {
			userCustomer cust = opuser.get();
			System.out.println("Got customer");
		}
		
		return null;
	}
	
	
	
	
	

}
