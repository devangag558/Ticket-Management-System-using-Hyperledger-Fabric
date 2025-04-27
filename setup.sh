cryptogen generate --config=crypto-config.yaml


docker-compose -f docker-compose-all.yaml down

docker-compose -f docker-compose-all.yaml up -d


export FABRIC_CA_CLIENT_HOME=/home/greenhat/hyperledger2/fabric-samples/Final-Network/



fabric-ca-client enroll -u https://peer0:peer0pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com" --enrollment.profile tls --csr.hosts peer0.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer1:peer1pw@localhost:13054 -M /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com
fabric-ca-client enroll -u https://peer2:peer2pw@localhost:13054 -M /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com
fabric-ca-client enroll -u https://peer3:peer3pw@localhost:13054 -M /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com
fabric-ca-client enroll -u http://peer3:peerpw@localhost:14054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/transport.com/users/peer3.transport.com
fabric-ca-client enroll -u http://peer0:peerpw@localhost:14054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/transport.com/users/peer0.transport.com
fabric-ca-client enroll -u http://peer1:peerpw@localhost:14054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/transport.com/users/peer1.transport.com
fabric-ca-client enroll -u http://peer2:peerpw@localhost:14054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/transport.com/users/peer2.transport.com
fabric-ca-client enroll -u http://admin1:adminpw@localhost:14054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/transport.com/users/admin1.transport.com/msp


fabric-ca-client register   --id.name peer0   --id.secret peer0pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer1   --id.secret peer1pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer2   --id.secret peer2pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer3   --id.secret peer3pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"

fabric-ca-client register   --id.name peer0   --id.secret peerpw   --id.type peer   -u http://localhost:14054
fabric-ca-client register   --id.name peer0   --id.secret peerpw   --id.type peer   -u http://localhost:14054
fabric-ca-client register   --id.name peer0   --id.secret peerpw   --id.type peer   -u http://localhost:14054
fabric-ca-client register   --id.name peer0   --id.secret peerpw   --id.type peer   -u http://localhost:14054
fabric-ca-client register   --id.name peer0   --id.secret peerpw   --id.type peer   -u http://localhost:14054
fabric-ca-client register   --id.name admin1   --id.secret adminpw   --id.type admin   -u http://localhost:14054




fabric-ca-client enroll -u https://peer0:peerpw@localhost:13054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com --enrollment.profile tls --csr.hosts ticket.com,transport.com,localhost
fabric-ca-client enroll -u https://peer1:peerpw@localhost:13054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com --enrollment.profile tls --csr.hosts ticket.com,transport.com,localhost
fabric-ca-client enroll -u https://peer2:peerpw@localhost:13054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com --enrollment.profile tls --csr.hosts ticket.com,transport.com,localhost
fabric-ca-client enroll -u http://peer3:peerpw@localhost:13054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com --enrollment.profile tls --csr.hosts ticket.com,transport.com,localhost
fabric-ca-client enroll -u http://peer1:peerpw@localhost:13054 --mspdir /home/greenhat/hyperledger2/fabric-samples/devang/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com --enrollment.profile tls --csr.hosts ticket.com,transport.com,localhost


export FABRIC_CA_CLIENT_HOME=${PWD}/crypto-config/peerOrganizations/ticket.com/

fabric-ca-client enroll -u https://admin:adminpw@localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"


fabric-ca-client register   --id.name peer0   --id.secret peer0pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer1   --id.secret peer1pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer2   --id.secret peer2pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer3   --id.secret peer3pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"

fabric-ca-client enroll -u https://peer0:peer0pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer1:peer1pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer2:peer2pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer3:peer3pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"


fabric-ca-client enroll -u https://peer0:peer0pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls" --enrollment.profile tls --csr.hosts peer0.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer1:peer1pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls" --enrollment.profile tls --csr.hosts peer1.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer2:peer2pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls" --enrollment.profile tls --csr.hosts peer2.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer3:peer3pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls" --enrollment.profile tls --csr.hosts peer3.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"


cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/tlscacerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/ca.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/signcerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/server.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/keystore/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/server.key"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/tlscacerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/ca.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/signcerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/server.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/keystore/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/server.key"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/tlscacerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/ca.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/signcerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/server.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/keystore/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/server.key"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/tlscacerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/ca.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/signcerts/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/server.crt"
cp "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/keystore/"* "${PWD}/crypto-config/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/server.key"


fabric-ca-client register   --id.name ticketadmin   --id.secret ticketadminpw   --id.type admin   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name user1   --id.secret user1pw   --id.type client   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"

fabric-ca-client enroll -u https://user1:user1pw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/users/user1.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://ticketadmin:ticketadminpw@localhost:13054 -M "${PWD}/crypto-config/peerOrganizations/ticket.com/users/Admin.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/fabric-ca/ticket.com/ca-cert.pem"

