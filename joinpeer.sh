#!/bin/bash

# List of your peer container names
#!/bin/bash

# List of your peer container names
PEERS=(
  peer1.ticket.com
  peer3.ticket.com
  peer0.ticket.com
  peer2.ticket.com
)

# rm -rf ./crypto-config
# rm -rf ./organizations

sudo -p 457847 rm -rf crypto-config/


sudo -p 457847 rm -rf organizations/

cryptogen generate --config=crypto-config.yaml

# rm -rf ./organizations


echo "=========================="
echo "Taking down the network if already up"
echo "=========================="
docker-compose down

docker-compose -f docker-compose-all.yaml down --remove-orphans

docker volume prune -f

docker-compose -f docker-compose-all.yaml up -d


sleep 10

export FABRIC_CA_CLIENT_HOME=${PWD}/organizations/peerOrganizations/ticket.com/


mkdir -p ./organizations/peerOrganizations/ticket.com/msp/tlscacerts

# echo 'NodeOUs:
#   Enable: true
#   ClientOUIdentifier:
#     Certificate: cacerts/localhost-13054.pem
#     OrganizationalUnitIdentifier: client
#   PeerOUIdentifier:
#     Certificate: cacerts/localhost-13054.pem
#     OrganizationalUnitIdentifier: peer
#   AdminOUIdentifier:
#     Certificate: cacerts/localhost-13054.pem
#     OrganizationalUnitIdentifier: admin
#   OrdererOUIdentifier:
#     Certificate: cacerts/localhost-13054.pem
#     OrganizationalUnitIdentifier: orderer' > "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml"


cp ./config.yaml ./organizations/peerOrganizations/ticket.com/msp/ -v
echo "./organizations/peerOrganizations/ticket.com/msp"
sleep 5

ls ./organizations/peerOrganizations/ticket.com/msp/

sleep 5


# Copy org1's CA cert to org1's /msp/tlscacerts directory (for use in the channel MSP definition)
cp -v "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem" "${PWD}/organizations/peerOrganizations/ticket.com/msp/tlscacerts/ca.crt"

mkdir -p "${PWD}/organizations/peerOrganizations/ticket.com/tlsca"
cp -v "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem" "${PWD}/organizations/peerOrganizations/ticket.com/tlsca/tlsca.ticket.com-cert.pem"

mkdir -p "${PWD}/organizations/peerOrganizations/ticket.com/ca"
cp -v "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem" "${PWD}/organizations/peerOrganizations/ticket.com/ca/ca.ticket.com-cert.pem"

sleep 10

fabric-ca-client enroll -u https://admin:adminpw@localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"


fabric-ca-client register   --id.name peer0   --id.secret peer0pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer1   --id.secret peer1pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer2   --id.secret peer2pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name peer3   --id.secret peer3pw   --id.type peer   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"

sleep 5

fabric-ca-client enroll -u https://peer0:peer0pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer1:peer1pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer2:peer2pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer3:peer3pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"

sleep 5

fabric-ca-client enroll -u https://peer0:peer0pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls" --enrollment.profile tls --csr.hosts peer0.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer1:peer1pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls" --enrollment.profile tls --csr.hosts peer1.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer2:peer2pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls" --enrollment.profile tls --csr.hosts peer2.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://peer3:peer3pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls" --enrollment.profile tls --csr.hosts peer3.ticket.com --csr.hosts localhost --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"

sleep 5

cp -v ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/tlscacerts/* ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/ca.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/signcerts/* ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/server.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/keystore/* ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/server.key
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/tlscacerts/* ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/ca.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/signcerts/* ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/server.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/keystore/* ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/server.key
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/tlscacerts/* ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/ca.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/signcerts/* ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/server.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/keystore/* ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/server.key
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/tlscacerts/* ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/ca.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/signcerts/* ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/server.crt
cp -v ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/keystore/* ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/tls/server.key

sleep 5

fabric-ca-client register   --id.name ticketadmin   --id.secret ticketadminpw   --id.type admin   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client register   --id.name user1   --id.secret user1pw   --id.type client   -u https://localhost:13054 --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"

fabric-ca-client enroll -u https://user1:user1pw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/users/user1@ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"
fabric-ca-client enroll -u https://ticketadmin:ticketadminpw@localhost:13054 -M "${PWD}/organizations/peerOrganizations/ticket.com/users/Admin@ticket.com/msp" --tls.certfiles "${PWD}/crypto-config/peerOrganizations/ticket.com/ca-cert.pem"

cp ./config.yaml ./organizations/peerOrganizations/ticket.com/peers/peer0.ticket.com/msp/ -v
cp ./config.yaml ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/msp/ -v
cp ./config.yaml ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/msp/ -v
cp ./config.yaml ./organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/msp/ -v
cp ./config.yaml ./organizations/peerOrganizations/ticket.com/users/Admin@ticket.com/msp/ -v
cp ./config.yaml ./organizations/peerOrganizations/ticket.com/users/user1@ticket.com/msp/ -v

cp ./connection-ticket.json ./organizations/peerOrganizations/ticket.com/ -v
# cp "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml" "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/msp/"
# cp "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml" "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/msp/"
# cp "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml" "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/msp/"

# cp "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml" "${PWD}/organizations/peerOrganizations/ticket.com/users/Admin@ticket.com/msp/"
# cp "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml" "${PWD}/organizations/peerOrganizations/ticket.com/users/user1@ticket.com/msp/"
# cp "${PWD}/organizations/peerOrganizations/ticket.com/msp/config.yaml" "${PWD}/organizations/peerOrganizations/ticket.com/peers/peer3.ticket.com/msp/"



sleep 10

rm -rf ./channel-artifacts

mkdir ./channel-artifacts

echo "=========================="
echo "Generating channel artifacts"
echo "=========================="
configtxgen -profile OrdererGenesis -channelID system-channel -outputBlock ./channel-artifacts/genesis.block
configtxgen -profile TicketChannel -outputCreateChannelTx ./channel-artifacts/channel.tx -channelID mychannel


sleep 10

echo "=========================="
echo "Starting the peers and bringing up the overall network"
echo "=========================="

docker-compose up -d

sleep 10

docker ps -a


echo "=========================="
echo "Creating a channel and copying mychannel.block to local directory"
echo "=========================="
docker exec peer0.ticket.com bash -c "peer channel create -o orderer1.orderer.com:7050 --ordererTLSHostnameOverride orderer1.orderer.com -c mychannel -f /etc/hyperledger/configtx/channel.tx --outputBlock /etc/hyperledger/configtx/mychannel.block --tls --cafile \$ORDERER_CA"

sleep 10

docker cp peer0.ticket.com:/etc/hyperledger/configtx/mychannel.block .

sleep 10

echo "=========================="
echo "Copying mychannel.block to rest of the peers"
echo "=========================="
docker cp mychannel.block peer1.ticket.com:/etc/hyperledger/configtx/mychannel.block
docker cp mychannel.block peer2.ticket.com:/etc/hyperledger/configtx/mychannel.block
docker cp mychannel.block peer3.ticket.com:/etc/hyperledger/configtx/mychannel.block
# docker cp mychannel.block peer0.transport.com:/etc/hyperledger/configtx/mychannel.block
# docker cp mychannel.block peer1.transport.com:/etc/hyperledger/configtx/mychannel.block

sleep 10

# Command to run inside each container
COMMAND2=""
COMMAND1="peer channel join -b /etc/hyperledger/configtx/mychannel.block"


# Loop over each peer and run the join command
for PEER in "${PEERS[@]}"
do
  echo "=========================="
  echo "Joining channel on $PEER..."
  echo "=========================="
  docker exec $PEER bash -c "export CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@ticket.com/msp && peer channel join -b /etc/hyperledger/configtx/mychannel.block"
#   echo "export done"
#   sleep 4
#   docker exec $PEER bash -c "$COMMAND1"
# #   docker exec $PEER bash -c "$COMMAND"
done

echo "=========================="
echo "Making an anchor update and copying it to anchor peers"
echo "=========================="
# configtxgen -profile ChannelProfile -outputAnchorPeersUpdate ./TransportMSPanchors.tx -channelID mychannel -asOrg TransportOrgMSP
configtxgen -profile TicketChannel -outputAnchorPeersUpdate ./TicketMSPanchors.tx -channelID mychannel -asOrg TicketOrgMSP
docker cp TicketMSPanchors.tx peer0.ticket.com:/etc/hyperledger/configtx/TicketMSPanchors.tx
# docker cp TransportMSPanchors.tx peer0.transport.com:/etc/hyperledger/configtx/TransportMSPanchors.tx

sleep 10


# docker exec peer1.ticket.com bash -c "peer channel join -b /etc/hyperledger/configtx/mychannel.block"
# docker exec peer2.ticket.com bash -c "peer channel join -b /etc/hyperledger/configtx/mychannel.block"
# docker exec peer3.ticket.com bash -c "peer channel join -b /etc/hyperledger/configtx/mychannel.block"
# docker exec peer1.ticket.com bash -c "peer channel join -b /etc/hyperledger/configtx/mychannel.block"

echo "=========================="
echo "Updating the anchors"
echo "=========================="

sleep 10
docker exec peer0.ticket.com bash -c "peer channel update -o orderer1.orderer.com:7050 --ordererTLSHostnameOverride orderer1.orderer.com -c mychannel -f /etc/hyperledger/configtx/TicketMSPanchors.tx --tls --cafile \$ORDERER_CA"
sleep 10
# docker exec peer0.transport.com bash -c "peer channel update -o orderer1.orderer.com:7050 --ordererTLSHostnameOverride orderer1.orderer.com -c mychannel -f /etc/hyperledger/configtx/TransportMSPanchors.tx --tls --cafile \$ORDERER_CA"

echo "✅ All peers have attempted to join the channel. Anchor peers set."




echo "=========================="
echo "Updating packages at peer0.ticket.com and installing chaincode"
echo "=========================="

# docker exec peer0.transport.com bash -c "add-apt-repository ppa:longsleep/golang-backports"

docker exec peer0.ticket.com bash -c "apt update"
# docker exec peer0.ticket.com bash -c "apt install nano inetutils-ping golang git -y"

docker cp ./stake.tar.gz peer0.ticket.com:/tmp/

docker exec peer0.ticket.com bash -c "peer lifecycle chaincode install /tmp/stake.tar.gz"

echo "✅ All modules have been updated on peer0.ticket.com"

docker exec peer0.ticket.com bash -c "peer lifecycle chaincode approveformyorg   --channelID mychannel   --name stake_1   --version 1   --package-id stake_1:a8c661e2552179d7561a12090728cd52986d66dc7046a30247f3ea08a3e331e5   --sequence 1   --orderer orderer1.orderer.com:7050   --tls --cafile \$ORDERER_CA "

sleep 30

docker exec peer0.ticket.com bash -c "peer lifecycle chaincode commit   --name stake_1   --version 1   --sequence 1   --orderer orderer1.orderer.com:7050    --tls --cafile \$ORDERER_CA   --peerAddresses peer0.ticket.com:7051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt --peerAddresses peer1.ticket.com:8051 --tlsRootCertFiles ./organizations/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/ca.crt --peerAddresses peer2.ticket.com:9051 --tlsRootCertFiles ./organizations/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/ca.crt --channelID mychannel"

# docker exec peer0.ticket.com bash -c "peer lifecycle chaincode commit  --channelID mychannel   --name stake_1   --version 1   --sequence 1   --orderer orderer1.orderer.com:7050    --tls --cafile \$ORDERER_CA   --peerAddresses peer0.ticket.com:7051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt  --peerAddresses peer2.ticket.com:9051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt"

# docker cp ./chaincode/tars/transportChaincode/transportcc.tar.gz peer0.transport.com:/tmp/

# docker exec peer0.transport.com bash -c "peer lifecycle chaincode install /tmp/transportcc.tar.gz"


# echo "✅ Chaincode has been installed on peer0.transport.com"



# docker exec peer1.transport.com bash -c "apt update"
# docker exec peer0.ticket.com bash -c "apt update"
# docker exec peer1.ticket.com bash -c "apt update"
# docker exec peer2.ticket.com bash -c "apt update"
# docker exec peer3.ticket.com bash -c "apt update"


# docker exec peer0.transport.com bash -c "apt install nano inetutils-ping -y"
# docker exec peer1.transport.com bash -c "apt install nano inetutils-ping -y"
# docker exec peer0.ticket.com bash -c "apt install nano inetutils-ping -y"
# docker exec peer1.ticket.com bash -c "apt install nano inetutils-ping -y"
# docker exec peer2.ticket.com bash -c "apt install nano inetutils-ping -y"
# docker exec peer3.ticket.com bash -c "apt install nano inetutils-ping -y"





# sleep 40

# docker-compose -f ../../blockchain-explorer/docker-compose.yaml down
# docker-compose -f ../../blockchain-explorer/docker-compose.yaml up -d

# sleep 100


# echo "=========================="
# echo "Copying and Installing the chaincode"
# echo "=========================="


# docker cp ../../Projects/ticketcontract_1.tar.gz peer0.transport.com:/tmp/


# docker cp ../../Projects/ticketcontract_1.tar.gz peer1.transport.com:/tmp/



# docker exec peer0.transport.com bash -c "peer lifecycle chaincode install /tmp/ticketcontract_1.tar.gz"

# sleep 100

# docker exec peer0.ticket.com bash -c "peer lifecycle chaincode approveformyorg   --channelID mychannel   --name stake_1   --version 1   --package-id stake_1:a8c661e2552179d7561a12090728cd52986d66dc7046a30247f3ea08a3e331e5   --sequence 1   --orderer orderer1.orderer.com:7050   --tls --cafile \$ORDERER_CA "

# docker exec peer0.ticket.com bash -c "peer lifecycle chaincode approveformyorg   --channelID mychannel   --name transportcc_1   --version 1   --package-id transportcc_1:3c4a6c0b17796e9def40576e749d75f81ef682250db0cbbaa271e27b0b4ec6fb   --sequence 1  --orderer orderer1.orderer.com:7050   --tls --cafile \$ORDERER_CA "


# docker exec peer0.transport.com bash -c "peer lifecycle chaincode approveformyorg   --channelID mychannel   --name ticket_1   --version 1   --package-id ticket_1:680d993eb7b3aecfa9be438ecc5b2d7e9c061ad9e576060ba02f3967927ffa1e   --sequence 1    --orderer orderer1.orderer.com:7050   --tls --cafile \$ORDERER_CA "

# docker exec peer0.ticket.com bash -c "peer lifecycle chaincode approveformyorg   --channelID mychannel   --name ticket_1   --version 1   --package-id ticket_1:680d993eb7b3aecfa9be438ecc5b2d7e9c061ad9e576060ba02f3967927ffa1e   --sequence 1   --orderer orderer1.orderer.com:7050   --tls --cafile \$ORDERER_CA "


# # to get contract to install
# sleep 50
# # peer lifecycle chaincode install /tmp/ticketcontract_1.tar.gz

# docker exec peer0.transport.com bash -c "peer lifecycle chaincode checkcommitreadiness --channelID mychannel --name transportcc_1  --version 1 --sequence 1 --output json"

# #  to get organization approval of a contract

# peer chaincode invoke \
#   --orderer orderer1.orderer.com:7050 \
#   --tls --cafile $ORDERER_CA \
#   -C mychannel \
#   -n ticketcontract_1 \
#   --isInit \
#   --peerAddresses peer0.transport.com:11051 \
#   --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt \
#   -c '{"function":"init","Args":[]}'

# # peer lifecycle chaincode approveformyorg \
# #   --channelID mychannel \
# #   --name ticketcontract_1 \
# #   --version 1 \
# #   --package-id ticketcontract_1:efc99eced5e97ae053b4230a4e4eb79b19b53be90dbc4caf1e809e1ff5d3c07a \
# #   --sequence 1 \
# #   --init-required \
# #   --orderer orderer1.orderer.com:7050 \
# #   --tls --cafile $ORDERER_CA                     

# sleep 50

# docker exec peer0.transport.com bash -c "peer lifecycle chaincode commit  --channelID mychannel   --name transportcc_1   --version 1   --sequence 1   --orderer orderer1.orderer.com:7050    --tls --cafile \$ORDERER_CA   --peerAddresses peer0.ticket.com:7051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/ca.crt  --peerAddresses peer1.ticket.com:8051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/ca.crt   --peerAddresses peer2.ticket.com:9051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/ca.crt --peerAddresses peer0.transport.com:11051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt    --peerAddresses peer1.transport.com:12051 --tlsRootCertFiles /crypto-config/peerOrganizations/transport.com/peers/peer1.transport.com/tls/ca.crt"


# docker exec peer0.transport.com bash -c "peer lifecycle chaincode commit  --channelID mychannel   --name myassetcontract   --version 1   --sequence 1    --init-required    --orderer orderer1.orderer.com:7050    --tls --cafile \$ORDERER_CA   --peerAddresses peer0.ticket.com:7051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/ca.crt  --peerAddresses peer1.ticket.com:8051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/ca.crt   --peerAddresses peer2.ticket.com:9051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/ca.crt --peerAddresses peer0.transport.com:11051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt    --peerAddresses peer1.transport.com:12051 --tlsRootCertFiles /crypto-config/peerOrganizations/transport.com/peers/peer1.transport.com/tls/ca.crt"


# peer chaincode invoke \
#   -o orderer1.orderer.com:7050 \
#   --ordererTLSHostnameOverride orderer1.orderer.com:7050 \
#   --tls \
#   --cafile $ORDERER_CA \
#   -C mychannel \
#   -n ticketcontract_1 \
#   --peerAddresses peer0.transport.com:11051 \
#   --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt \
#   -c '{"function":"createTicket","Args":["bus", "MyBusService", "2h", "10:00", "12:00", "Station A", "paid"]}'


# #to verify the approval
# #  peer lifecycle chaincode checkcommitreadiness --channelID mychannel --name ticketcontract_1  --version 1 --sequence 1 --init-required --output json


# # peer lifecycle chaincode commit  --channelID mychannel   --name ticketcontract_1   --version 1   --sequence 1    --init-required    --orderer orderer1.orderer.com:7050    --tls --cafile $ORDERER_CA   --peerAddresses peer0.ticket.com:7051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer0.ticket.com/tls/ca.crt  --peerAddresses peer1.ticket.com:8051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer1.ticket.com/tls/ca.crt   --peerAddresses peer2.ticket.com:9051 --tlsRootCertFiles /crypto-config/peerOrganizations/ticket.com/peers/peer2.ticket.com/tls/ca.crt --peerAddresses peer0.transport.com:11051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt    --peerAddresses peer1.transport.com:12051 --tlsRootCertFiles /crypto-config/peerOrganizations/transport.com/peers/peer1.transport.com/tls/ca.crt



# # Ok — thanks for the update — **I see the problem very clearly now**! 👀

# # ---

# # ### ⚠️ You are mixing correct and wrong TLS certs during your `commit` command.

# # In your command:

# # ```bash
# # --peerAddresses peer0.transport.com:11051 --tlsRootCertFiles /etc/hyperledger/msp/peer/tls/ca.crt
# # ```

# # 🔴 `/etc/hyperledger/msp/peer/tls/ca.crt` is **wrong** — it’s not matching your other peer cert paths.

# # The correct format, like you did for others, should be:

# # ```bash
# # /crypto-config/peerOrganizations/transport.com/peers/peer0.transport.com/tls/ca.crt
# # ```

# # ---
  
# # ### ✨ Here’s the **fixed** `peer lifecycle chaincode commit` command you should use:

# # ```bash
# # peer lifecycle chaincode commit \
# #   --channelID mychannel \
# #   --name ticketcontract_1 \
# #   --version 1 \
# #   --sequence 1 \
# #   --init-required \
# #   --orderer orderer1.orderer.com:7050 \
# #   --tls \
# #   --cafile $ORDERER_CA \
# #   --peerAddresses peer0.transport.com:11051 \
# #   --tlsRootCertFiles /crypto-config/peerOrganizations/transport.com/peers/peer0.transport.com/tls/ca.crt 
# # ```

# # **Notice**:  
# # ✅ Now both `peer0.transport.com` and `peer1.transport.com` use the **right TLS cert paths**.

# # ---

# # # 🔥 After fixing this, when you re-run `peer lifecycle chaincode commit`, it should **SUCCEED**!

# # ✅ No more ENDORSEMENT_POLICY_FAILURE  
# # ✅ Your chaincode will be committed properly.

# # ---

# # # 💬 Want me to also give you the next step command to **init the chaincode** after commit? (You need to call `peer chaincode invoke -C mychannel -n ticketcontract_1 --isInit` once after commit.)  
# # Let’s finish this properly! 🚀

# Query installed contracts
# peer lifecycle chaincode queryinstalled   --peerAddresses peer0.transport.com:11051   --tlsRootCertFiles ./crypto-config/peerOrganizations/transport.com/peers/peer0.transport.com/tls/ca.crt


# make package
#  peer lifecycle chaincode package ticket.tar.gz   --path .   --lang golang   --label ticket_1 
