package org.example;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class App {
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) {
		try {
			registerCustomer("appUser43@gmail.com", "appUser6", "provider1", "7893828937");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String registerCustomer(String email, String password, String name, String contact){
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

			// Check to see if we've already enrolled the user.
			if (wallet.get(email) != null) {
				System.out.println("An identity for the user "+ name + " already exists in the wallet");
				return "An identity for the user already exists in the wallet";
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

			System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
			Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

			Gateway.Builder builder = Gateway.createBuilder()
					.identity(wallet, email)
					.networkConfig(networkConfigPath)
					.discovery(false);

			try (Gateway gateway = builder.connect()) {
				System.out.println("Connection successful");
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("stake_1");

				byte[] result = contract.submitTransaction("registerCustomer", name, contact);
				String resultString = new String(result);
				System.out.println("Transaction successful. Result: " + resultString);
				return resultString;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public static void registerProviderDynamic(String enrollmentID, String password, String name, String contact, int rating, String serviceProvider) throws Exception {
		if (enrollmentID == null || password == null || name == null || contact == null || serviceProvider == null) {
			throw new IllegalArgumentException("Required fields are missing");
		}
		Properties props = new Properties();
		props.put("pemFile",
				"../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:13054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallet"));

		// Check to see if we've already enrolled the user.
		if (wallet.get(enrollmentID) != null) {
			System.out.println("An identity for the user \"appUser\" already exists in the wallet");
			return;
		}
		
		//		
		Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
		
		X509Identity adminIdentity = Identities.newX509Identity("TicketOrgMSP", adminEnrollment);
        wallet.put("admin", adminIdentity);
        System.out.println("Successfully enrolled admin user \"admin\" and imported it into the wallet");
        
		User adminUser = new User() {
			public String getName() { return "admin"; }
			public Set<String> getRoles() { return null; }
			public String getAccount() { return null; }
			public String getAffiliation() { return "ticket.department1"; }
			public Enrollment getEnrollment() { return adminEnrollment; }
			public String getMspId() { return "TicketMSP"; }
		};

		RegistrationRequest rr = new RegistrationRequest(enrollmentID);
		rr.setType("client");
		rr.setSecret(password);
		String enrollmentSecret = caClient.register(rr, adminUser);
		
		System.out.println(password);
		System.out.println(enrollmentSecret);
		Enrollment enrollment = caClient.enroll(enrollmentID, enrollmentSecret);

		Identity user = Identities.newX509Identity("TicketOrgMSP", enrollment);
		wallet.put(enrollmentID, user);
		System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
		Path networkConfigPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

		Gateway.Builder builder = Gateway.createBuilder()
				.identity(wallet, enrollmentID)
				.networkConfig(networkConfigPath)
				.discovery(false);

		try (Gateway gateway = builder.connect()) {
			System.out.println("Connection successful");
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("stake_1");

			byte[] result = contract.submitTransaction("registerProvider", name, contact, String.valueOf(rating), serviceProvider);
			System.out.println("Transaction successful. Result: " + new String(result));
		}
	}
}


