package org.example;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class getServiceProviderDetails {
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
    public static String getProviderDetails(String providerEmail) throws Exception {
        if (providerEmail == null || providerEmail.isEmpty()) {
            throw new IllegalArgumentException("Provider email parameter is required.");
        }

        // Load the network configuration
        Path ccpPath = Paths.get("..", "..", "..", "organizations", "peerOrganizations", "ticket.com", "connection-ticket.json");

        // Setup wallet path
        Path walletPath = Paths.get("../../wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);

        // Check if the identity exists
        Identity identity = wallet.get(providerEmail);
        if (identity == null) {
            throw new Exception("An identity for the provider \"" + providerEmail + "\" does not exist in the wallet");
        }

        Gateway.Builder builder = Gateway.createBuilder()
            .identity(wallet, providerEmail)
            .networkConfig(ccpPath)
            .discovery(false);

        try (Gateway gateway = builder.connect()) {
            // Get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("stake_1");
            System.out.println("connection successful");
            // Evaluate the transaction
            System.out.println("Evaluating transaction: getProviderDetails");
            byte[] result = contract.evaluateTransaction("getProviderDetails");
            String resultStr = new String(result);
            System.out.println("Transaction result: " + resultStr);
            return resultStr;
        } catch (Exception e) {
            System.err.println("Failed to get provider details: " + e.getMessage());
            throw new Exception("Chaincode error: " + e.getMessage());
        }
    }

    // For running the Java file from command line
    public static void main(String[] args) {
        try {
            String result = getProviderDetails("appUser2@gmail.com");
            System.out.println("Chaincode Result: " + result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
