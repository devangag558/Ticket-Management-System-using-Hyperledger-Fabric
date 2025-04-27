package org.example.service;

import org.apache.commons.lang3.tuple.Pair;
import org.example.entity.vehicle;
import org.example.ledger.dto.bookTicketData;
import org.example.ledger.dto.ticketJson;
import org.example.ledger.dto.vehicleJson;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.protos.common.Ledger.BlockchainInfo;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class ledgerService {
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
	
	
	public boolean registerNewCustomer(String email, String password, String name, String contact){
		try {


			String currentPath = new File("").getAbsolutePath();
			System.out.println("Current working directory: " + currentPath);
			if (email == null || password == null || name == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			// Check to see if we've already enrolled the user.
			if (wallet.get(email) != null) {
				System.out.println("An identity for the user "+ name + " already exists in the wallet");
				//				return true;
			}

			Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");


			User adminUser = new User() {
				public String getName() { return "admin"; }
				public Set<String> getRoles() { return null; }
				public String getAccount() { return null; }
				public String getAffiliation() { return "ticket.department1"; }
				public Enrollment getEnrollment() { return adminEnrollment; }
				public String getMspId() { return "TicketMSP"; }
			};

			RegistrationRequest rr = new RegistrationRequest(email);
			rr.setType("client");
			rr.setSecret(password);
			String enrollmentSecret = caClient.register(rr, adminUser);

			System.out.println(password);
			System.out.println(enrollmentSecret);
			Enrollment enrollment = caClient.enroll(email, enrollmentSecret);

			Identity userIdentity = Identities.newX509Identity("TicketOrgMSP", enrollment);
			wallet.put(email, userIdentity);
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}


		
	
	public boolean registerNewSeller(String email, String password, String name, String contact){
		try {


			String currentPath = new File("").getAbsolutePath();
			System.out.println("Current working directory: " + currentPath);
			if (email == null || password == null || name == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			// Check to see if we've already enrolled the user.
			if (wallet.get(email) != null) {
				System.out.println("An identity for the user "+ name + " already exists in the wallet");
				//				return true;
			}

			Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");


			User adminUser = new User() {
				public String getName() { return "admin"; }
				public Set<String> getRoles() { return null; }
				public String getAccount() { return null; }
				public String getAffiliation() { return "ticket.department1"; }
				public Enrollment getEnrollment() { return adminEnrollment; }
				public String getMspId() { return "TicketMSP"; }
			};

			RegistrationRequest rr = new RegistrationRequest(email);
			rr.setType("client");
			rr.setSecret(password);
			String enrollmentSecret = caClient.register(rr, adminUser);

			System.out.println(password);
			System.out.println(enrollmentSecret);
			Enrollment enrollment = caClient.enroll(email, enrollmentSecret);

			Identity userIdentity = Identities.newX509Identity("TicketOrgMSP", enrollment);
			wallet.put(email, userIdentity);
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	
	public boolean putOnLedgerSellerNew(String email, String password, String name, String gstNumber, String address, String city){
		try {
			if (email == null || password == null || name == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			caClient.enroll(email, password);

			System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");

				byte[] result = contract.submitTransaction("registerSeller", name, gstNumber, address, city);
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return true;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	


	public boolean putOnLedgerNew(String email, String password, String name, String address, String city, String gender, int age){
		try {
			if (email == null || password == null || name == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			caClient.enroll(email, password);

			System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");

				byte[] result = contract.submitTransaction("registerCustomer", name, address,city,gender,String.valueOf(age));
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return true;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	
	
	public boolean updateOnLedgerSeller(String email, String name, String address, String city){
		try {
			if (email == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			Identity identity = wallet.get(email);
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + email + "\" does not exist in the wallet");
			}
			
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				

				if (name ==null) name = "null";
				if (address == null) address="null";
				if (city == null) city ="null";

				byte[] result = contract.submitTransaction("updateSellerDetails", name, address, city);
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return true;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	public boolean updateOnLedger(String email, String name, String address, String city, String gender, int age){
		try {
			if (email == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			Identity identity = wallet.get(email);
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + email + "\" does not exist in the wallet");
			}
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				

				if (name ==null) name = "null";
				if (address == null) address="null";
				if (city == null) city ="null";
				if (gender == null) city ="null";
				

				byte[] result = contract.submitTransaction("updateCustomerDetails", name, address,city,gender,String.valueOf(age));
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return true;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean addvehicle(String email,String vehicleId, String source,String destination,String departureDate,String departureTime,String mode,String seatCapacity,String basePrice){
		try {
			if (email == null) {
				throw new IllegalArgumentException("Required fields are missing");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			Identity identity = wallet.get(email);
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + email + "\" does not exist in the wallet");
			}
			
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				

				byte[] result = contract.submitTransaction("addVehicle", vehicleId, source, destination,departureDate,departureTime, mode, seatCapacity,basePrice);
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return true;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	
	
	public String getAllVehicles(){
		try {
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			Identity identity = wallet.get("admin");
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + "admin" + "\" does not exist in the wallet");
			}
			
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, "admin")
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				

				byte[] result = contract.evaluateTransaction("getAllVehicles");
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return resultString;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public boolean newaddTransaction(String txnId,String source, String destination, String destPassword, float amount){
		try {
			if (txnId == null ||source == null || destination == null || amount == 0) {
				throw new IllegalArgumentException("Required fields are missing or amount is zero");
			}
			Properties props = new Properties();
			props.put("pemFile",
					"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
			props.put("allowAllHostNames", "true");

			Path cert = Paths.get("../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

			System.out.println(new String(Files.readAllBytes(cert)));
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);

			// Create a wallet for managing identities
			Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

			caClient.enroll(destination, destPassword);

			System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, destination)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");

				byte[] result = contract.submitTransaction("addTransaction", txnId, source, destination, String.valueOf(amount));
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				gateway.close();
				return true;

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}


	
	public String getCustomerDetailsNew(String email) {
		try {
			if (email == null || email.isEmpty()) {
				throw new IllegalArgumentException("Provider email parameter is required.");
			}

			// Load the network configuration
			Path ccpPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			// Setup wallet path
			Path walletPath = Paths.get("../../wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);

			// Check if the identity exists
			Identity identity = wallet.get(email);
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + email + "\" does not exist in the wallet");
			}

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(ccpPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				// Get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				System.out.println("connection successful");
				// Evaluate the transaction
				System.out.println("Evaluating transaction: getCustomerDetails");
				byte[] result = contract.evaluateTransaction("getCustomerDetails");
				String resultStr = new String(result);
				System.out.println("Transaction result: " + resultStr);
				gateway.close();
				return resultStr;
			} catch (Exception e) {
				System.err.println("Failed to get provider details: " + e.getMessage());
				throw new Exception("Chaincode error: " + e.getMessage());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	public String getSellerDetailsNew(String email) {
		try {
			if (email == null || email.isEmpty()) {
				throw new IllegalArgumentException("Provider email parameter is required.");
			}

			// Load the network configuration
			Path ccpPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			// Setup wallet path
			Path walletPath = Paths.get("../../wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);

			// Check if the identity exists
			Identity identity = wallet.get(email);
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + email + "\" does not exist in the wallet");
			}

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(ccpPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				// Get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				System.out.println("connection successful");
				// Evaluate the transaction
				System.out.println("Evaluating transaction: getSellerDetails");
				byte[] result = contract.evaluateTransaction("getSellerDetails");
				String resultStr = new String(result);
				System.out.println("Transaction result: " + resultStr);
				gateway.close();
				return resultStr;
			} catch (Exception e) {
				System.err.println("Failed to get provider details: " + e.getMessage());
				throw new Exception("Chaincode error: " + e.getMessage());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}	
	public bookTicketData bookTicket(String email, String ticketId, String vehicleId, int seatCount,String txnId,float currentPrice) {
		try {
			if (email == null || email.isEmpty()) {
				throw new IllegalArgumentException("customer email parameter is required.");
			}
			
			System.out.println(email +" "+ticketId +" "+vehicleId+ " "+seatCount+" "+txnId+" "+currentPrice);

			// Load the network configuration
			Path ccpPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			// Setup wallet path
			Path walletPath = Paths.get("../../wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);

			// Check if the identity exists
			Identity identity = wallet.get(email);
			if (identity == null) {
				throw new Exception("An identity for the provider \"" + email + "\" does not exist in the wallet");
			}

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(ccpPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				// Get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("ticket_1");
				System.out.println("connection successful");
				// Evaluate the transaction
				System.out.println("Evaluating transaction: getSellerDetails"+txnId);
				byte[] result = contract.submitTransaction("bookTicket",ticketId,vehicleId,String.valueOf(seatCount),txnId,String.valueOf(currentPrice));
				String resultStr = new String(result);
				
				
				System.out.println("Transaction result: " + resultStr);
				
				Contract qsccContract = network.getContract("qscc");

				byte[] chainInfoBytes = qsccContract.evaluateTransaction("GetChainInfo", "mychannel");
				BlockchainInfo chainInfo = BlockchainInfo.parseFrom(chainInfoBytes);
				int currentBlockNumber = (int)chainInfo.getHeight() - 1; // Blocks are 0-indexed
				
				bookTicketData newTicket = new bookTicketData(resultStr);
				
				ticketJson newt = newTicket.getTicketData();
				
				newt.setBookingBlock(currentBlockNumber-1);
				
				newTicket.setTicketData(newt);
				
				gateway.close();
				return newTicket;
			} catch (Exception e) {
				System.err.println("Failed to get provider details: " + e.getMessage());
				throw new Exception("Chaincode error: " + e.getMessage());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			bookTicketData newt = new bookTicketData();
			return newt;
		}
	}	
	
	
	
}




