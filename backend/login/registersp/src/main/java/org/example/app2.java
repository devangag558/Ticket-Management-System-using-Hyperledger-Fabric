package org.example;

import org.hyperledger.fabric_ca.sdk.HFCAClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.gateway.Wallets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.Properties;

public class app2 {
	public static void main(String[] args) throws Exception {
        String walletPath = "../../wallet";
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(walletPath));

        if (wallet.get("admin") != null) {
            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
            return;
        }

        String ccpPath = "../../../organizations/peerOrganizations/ticket.com/connection-ticket.json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(ccpPath));

        JsonNode caNode = root.path("certificateAuthorities").path("ca.ticket.com");
        String caURL = caNode.path("url").asText();
        Path pemCertPath = Paths.get(caNode.path("tlsCACerts").path("path").asText());
        
        String pemCert = new String(Files.readAllBytes(pemCertPath));
        
        System.out.println(caURL);
        System.out.println(pemCert);

        File certFile = File.createTempFile("ca-cert", ".pem");
        java.nio.file.Files.write(certFile.toPath(), pemCert.getBytes());
        
        Properties props = new Properties();
        props.put("pemFile", "../../../crypto-config/peerOrganizations/ticket.com/ca-cert.pem");

        props.put("allowAllHostNames", "true");

        HFCAClient caClient = HFCAClient.createNewInstance( caURL, props);
        caClient.setCryptoSuite(org.hyperledger.fabric.sdk.security.CryptoSuite.Factory.getCryptoSuite());

        Enrollment enrollment = caClient.enroll("admin", "adminpw");

        X509Identity adminIdentity = org.hyperledger.fabric.gateway.Identities.newX509Identity("TicketOrgMSP", enrollment);
        wallet.put("admin", adminIdentity);
        System.out.println("Successfully enrolled admin user \"admin\" and imported it into the wallet");
        
    }
}
