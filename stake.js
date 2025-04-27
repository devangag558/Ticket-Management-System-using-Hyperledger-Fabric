'use strict';

const { Contract } = require('fabric-contract-api');


class TravelTicket extends Contract {

    async initLedger(ctx) {
        console.info('=== Initializing Ledger ===');
        console.info('Ledger initialization complete.');
    }

    async registerCustomer(ctx, name, address, city, gender, age) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const customerId = clientCN;
        const customerBytes = await ctx.stub.getState(customerId);
        if (customerBytes && customerBytes.length > 0) {
            throw new Error('Customer already registered.');
        }
        const customerData = {
            role: "customer",
            email: clientCN,
            name,
            address,
            city,
            gender,
            age,
            transactions: [],
            bookings: []  // list of ticket composite keys
        };
        await ctx.stub.putState(clientCN, Buffer.from(JSON.stringify(customerData)));
        return customerData;
    }

    async registerSeller(ctx, name, gstNumber, address, city) {
        const userId = ctx.clientIdentity.getID();
        
        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const sellerId = clientCN;
        
        const sellerBytes = await ctx.stub.getState(sellerId);
        if (sellerBytes && sellerBytes.length > 0) {
            throw new Error('Seller already registered.');
        }
        const sellerData = {
            role: "seller",
            email: sellerId,
            name,
            gstNumber,
            address,
            city,
            vehicles: [],
            transactions: []
        };
        await ctx.stub.putState(sellerId, Buffer.from(JSON.stringify(sellerData)));
        return sellerData;
    }

    async addTransaction(ctx, txnId, source, destination, amount) {
        const destBytes = await ctx.stub.getState(destination);
        if (!destBytes || destBytes.length === 0) {
            throw new Error('Source not registered.');
        }
        let destData = JSON.parse(sourceBytes.toString());

        const txnBytes = await ctx.stub.getState(txnId);
        if (sellerBytes && sellerBytes.length > 0) {
            throw new Error('Seller already registered.');
        }
        const txTimestamp = ctx.stub.getTxTimestamp();
        const timestamp = txTimestamp.seconds.low.toString();
        const txnData = {
            timestamp,
            txnId: txnId,
            source,
            destination,
            amount: parseFloat(amount)
        };
        if (!source == "app") {
            const sourceBytes = await ctx.stub.getState(source);
            if (!sourceBytes || sourceBytes.length === 0) {
                throw new Error('Source not registered.');
            }
            let sourceData = JSON.parse(sourceBytes.toString());

            await ctx.stub.putState(txnId, Buffer.from(JSON.stringify(txnData)));
            sourceData.transactions.push(txnData);
            destData.transactions.push(txnData);

            await ctx.stub.putState(source, Buffer.from(JSON.stringify(sourceData)));
            await ctx.stub.putState(destination, Buffer.from(JSON.stringify(destData)));
            return txnData;
        }
        else {
            await ctx.stub.putState(txnId, Buffer.from(JSON.stringify(txnData)));
            destData.transactions.push(txnData);
            await ctx.stub.putState(destination, Buffer.from(JSON.stringify(destData)));
            return txnData;
        }

    }

    async updateCustomerDetails(ctx, name, address, city, gender, age) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const customerId = clientCN;
        const customerBytes = await ctx.stub.getState(customerId);
        if (!customerBytes || customerBytes.length === 0) {
            throw new Error('Customer not registered.');
        }
        let customerData = JSON.parse(customerBytes.toString());

        customerData.name = name;
        customerData.address = address;
        customerData.city = city;
        customerData.gender = gender;
        customerData.age = age;

        await ctx.stub.putState(customerKey, Buffer.from(JSON.stringify(customerData)));
        return customerData;
    }
    async updateSellerDetails(ctx, name, address, city) {
        const userId = ctx.clientIdentity.getID();
        
        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const sellerId = clientCN;
        const sellerBytes = await ctx.stub.getState(sellerId);
        if (!sellerBytes || sellerBytes.length === 0) {
            throw new Error('Seller not registered.');
        }
        let sellerData = JSON.parse(sellerBytes.toString());


        sellerData.name = name || sellerData.name;
        sellerData.address = address || sellerData.address;
        sellerData.city = city || sellerData.city;
        await ctx.stub.putState(sellerKey, Buffer.from(JSON.stringify(sellerData)));
        return sellerData;
    }

    async addVehicle(ctx, vehicleId, source, destination, departureDate, departureTime, mode, seatCapacity, basePrice) {
        // Get seller info.
        const userId = ctx.clientIdentity.getID();
        
        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const sellerId = clientCN;
        const sellerBytes = await ctx.stub.getState(sellerId);
        if (!sellerBytes || sellerBytes.length === 0) {
            throw new Error('Seller not registered.');
        }
        let sellerData = JSON.parse(sellerBytes.toString());

        const vehicleBytes = await ctx.stub.getState(vehicleId);
        if (vehicleBytes && vehicleBytes.length > 0) {
            throw new Error('vehicle already registered.');
        }

        const vehicleData = {
            vehicleId: vehicleId,
            sellerId,
            source,
            destination,
            departureDate,
            departureTime,
            mode,
            seatCapacity: parseInt(seatCapacity),
            availableSeats: parseInt(seatCapacity),
            basePrice: parseFloat(basePrice)
        };
        await ctx.stub.putState(vehicleId, Buffer.from(JSON.stringify(vehicleData)));
        sellerData.vehicles.push(vehicleData);
        await ctx.stub.putState(sellerId, Buffer.from(JSON.stringify(sellerData)));
        return vehicleData;
    }
    async listVehicles(ctx, source, destination) {
        const iterator = await ctx.stub.getStateByPartialCompositeKey("vehicle", []);
        const options = [];
        while (true) {
            const res = await iterator.next();
            if (res.value && res.value.value.toString()) {
                const record = JSON.parse(res.value.value.toString('utf8'));
                if (record.source === source && record.destination === destination) {
                    options.push(record);
                }
            }
            if (res.done) {
                await iterator.close();
                return options;
            }
        }
    }


    async bookTicket(ctx, ticketId, vehicleId, seatCount, txnId) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];

        }
        const customerId = clientCN;
        const customerBytes = await ctx.stub.getState(customerId);
        if (!customerBytes || customerBytes.length === 0) {
            throw new Error('Customer not registered.');
        }
        let customerData = JSON.parse(customerBytes.toString());

        const txnBytes = await ctx.stub.getState(txnId);
        if (!txnBytes || txnBytes.length === 0) {
            throw new Error('Transaction does not exist.');
        }
        let txnData = JSON.parse(txnBytes.toString());
        const vehicleBytes = await ctx.stub.getState(vehicleId);
        if (!vehicleBytes || vehicleBytes.length === 0) {
            throw new Error('Vehicle does not exist.');
        }
        let vehicleData = JSON.parse(vehicleBytes.toString());
        vehicleData.availableSeats = vehicleData.availableSeats - seatCount;
        const basePrice = vehicleData.basePrice;
        const txTimestamp = ctx.stub.getTxTimestamp();
        const timestamp = txTimestamp.seconds.low.toString();
        const ticketData = {
            ticketId,
            vehicleId,
            customerId,
            seatCount,
            bookingTime: timestamp,
            pricePaid: txnData.amount,
            txnId: [],
            basePrice,
            isCancelled: false
        };

        ticketData.txnId.push(txnId);
        customerData.bookings.push(ticketId);
        await ctx.stub.putState(vehicleId, Buffer.from(JSON.stringify(vehicleData)));
        await ctx.stub.putState(customerId, Buffer.from(JSON.stringify(customerData)));
        await ctx.stub.putState(ticketId, Buffer.from(JSON.stringify(ticketData)));
        return ticketData;
    }

    async rescheduleTicket(ctx, ticketId, vehicleId, newtxnId) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        const clientCN = match[1];

        }
        const customerId = clientCN;
        const customerBytes = await ctx.stub.getState(customerId);
        if (!customerBytes || customerBytes.length === 0) {
            throw new Error('Customer not registered.');
        }
        let customerData = JSON.parse(customerBytes.toString());

        const txnBytes = await ctx.stub.getState(newtxnId);
        if (!txnBytes || txnBytes.length === 0) {
            throw new Error('Transaction does not exist.');
        }
        let txnData = JSON.parse(txnBytes.toString());
        const ticketBytes = await ctx.stub.getState(ticketId);
        if (!ticketBytes || ticketBytes.length === 0) {
            throw new Error('Ticket does not exist.');
        }
        let ticketData = JSON.parse(ticketBytes.toString());
        if (ticketData.isCancelled == true) {
            throw new Error('Ticket is already cancelled')
        }

        const newVehicleBytes = await ctx.stub.getState(vehicleId);
        if (!newVehicleBytes || newVehicleBytes.length === 0) {
            throw new Error('Vehicle does not exist.');
        }
        let newVehicleData = JSON.parse(newVehicleBytes.toString());


        const oldVehicleBytes = await ctx.stub.getState(ticketData.vehicleId);
        if (!oldVehicleBytes || oldVehicleBytes.length === 0) {
            throw new Error('Vehicle does not exist.');
        }
        let oldVehicleData = JSON.parse(oldVehicleBytes.toString());


        oldVehicleData.availableSeats = oldVehicleData.availableSeats + ticketData.seatCount;

        newVehicleData.availableSeats = newVehicleData.availableSeats - ticketData.seatCount;
        const basePrice = newVehicleData.basePrice;
        const txTimestamp = ctx.stub.getTxTimestamp();
        const timestamp = txTimestamp.seconds.low.toString();
        ticketData.basePrice = basePrice;
        ticketData.vehicleId = vehicleId;
        ticketData.bookingTime = timestamp;
        ticketData.pricePaid = txnData.amount;
        ticketData.txnId.push(txnId)
        await ctx.stub.putState(vehicleId, Buffer.from(JSON.stringify(newVehicleData)));
        await ctx.stub.putState(oldVehicleData.vehicleId, Buffer.from(JSON.stringify(oldVehicleData)));
        await ctx.stub.putState(ticketId, Buffer.from(JSON.stringify(ticketData)));
        return ticketData;
    }

    async cancelTicket(ctx, ticketId, txnId) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        
        const customerId = clientCN;
        const ticketKey = ticketId;
        const ticketBytes = await ctx.stub.getState(ticketKey);
        if (!ticketBytes || ticketBytes.length === 0) {
            throw new Error('Ticket does not exist.');
        }
        let ticketData = JSON.parse(ticketBytes.toString());
        if (ticketData.customerId !== customerId) {
            throw new Error('Not authorized to cancel this ticket.');
        }
        const vehicleKey = ticketData.vehicleId;
        const vehicleBytes = await ctx.stub.getState(vehicleKey);
        let vehicleData = JSON.parse(vehicleBytes.toString());

        const txnBytes = await ctx.stub.getState(txnId);
        if (!txnBytes || txnBytes.length === 0) {
            throw new Error('Transaction does not exist.');
        }
        let txnData = JSON.parse(txnBytes.toString());

        ticketData.isCancelled = true;
        ticketData.txnId.push(txnId)
        await ctx.stub.putState(ticketKey, Buffer.from(JSON.stringify(ticketData)));

        vehicleData.availableSeats += ticketData.seatCount;
        await ctx.stub.putState(vehicleKey, Buffer.from(JSON.stringify(vehicleData)));


        const customerKey = customerId;
        const customerBytes = await ctx.stub.getState(customerKey);
        let customerData = JSON.parse(customerBytes.toString());

        customerData.transactions.push(txnId);

        if (customerData.bookings && Array.isArray(customerData.bookings)) {
            const index = customerData.bookings.indexOf(ticketId);
            if (index !== -1) {
                customerData.bookings.splice(index, 1);
            }
        }

        await ctx.stub.putState(customerKey, Buffer.from(JSON.stringify(customerData)));

        return ticketData;
    }

    async deleteVehicle(ctx, vehicleId) {
        const userId = ctx.clientIdentity.getID();
        
        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const sellerId = clientCN;
        const vehicleKey = ctx.stub.createCompositeKey(vehiclePrefix, [vehicleId]);
        const vehicleBytes = await ctx.stub.getState(vehicleKey);
        if (!vehicleBytes || vehicleBytes.length === 0) {
            throw new Error('Vehicle does not exist.');
        }
        const vehicleData = JSON.parse(vehicleBytes.toString());
        if (vehicleData.sellerId !== sellerId) {
            throw new Error('Not authorized to delete this vehicle.');
        }
        if (vehicleData.availableSeats !== vehicleData.seatCapacity) {
            throw new Error('Cannot delete vehicle with active bookings.');
        }
        await ctx.stub.deleteState(vehicleKey);
        return { message: vehicleId + 'deleted successfully.' };
    }

    async getCustomerTickets(ctx) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const customerId = clientCN;
        
        const customerBytes = await ctx.stub.getState(customerId);
        if (!customerBytes || customerBytes.length === 0) {
            throw new Error('Customer not registered.');
        }
        const customerData = JSON.parse(customerBytes.toString());
        const tickets = [];
        for (const ticketKey of customerData.bookings) {
            const ticketBytes = await ctx.stub.getState(ticketKey);
            if (ticketBytes && ticketBytes.length > 0) {
                tickets.push(JSON.parse(ticketBytes.toString()));
            }
        }
        return tickets;
    }
    async getCustomerDetails(ctx) {
        const userId = ctx.clientIdentity.getID();

        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const customerId = clientCN;
        
        const customerBytes = await ctx.stub.getState(customerId);
        if (!customerBytes || customerBytes.length === 0) {
            throw new Error('Customer not registered.');
        }
        return customerBytes.toString();
    }

    async getTicketDetails(ctx, encodedTicketId) {
        const compositeKey = encodedTicketId;
        const ticketBytes = await ctx.stub.getState(compositeKey);
        if (!ticketBytes || ticketBytes.length === 0) {
            throw new Error('Ticket does not exist.');
        }
        return ticketBytes.toString();
    }




    async getSellerVehicles(ctx) {
        const userId = ctx.clientIdentity.getID();
        
        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const sellerId = clientCN;
        const sellerBytes = await ctx.stub.getState(sellerId);
        if (!sellerBytes || sellerBytes.length === 0) {
            throw new Error('Seller not registered or deleted');
        }
        let sellerData = JSON.parse(sellerBytes.toString());
        let vehicles = [];
        for (const vehicleId of sellerData.vehicles) {
            const vehicleBytes = await ctx.stub.getState(vehicleId);
            if (vehicleBytes && vehicleBytes.length > 0) {
                vehicles.push(JSON.parse(vehicleBytes.toString()));
            }
        }
        return vehicles;
    }

    async getSellerDetails(ctx) {
        const userId = ctx.clientIdentity.getID();
        
        const regex = /CN=([^:]+)/;
        const match = userId.match(regex);

        let clientCN = null;
        if (match && match[1]) {
        clientCN = match[1];
        }
        const sellerId = clientCN;
        
        const sellerKey = ctx.stub.createCompositeKey(sellerPrefix, [sellerId]);
        const sellerBytes = await ctx.stub.getState(sellerKey);
        if (!sellerBytes || sellerBytes.length === 0) {
            throw new Error('Seller not registered.');
        }
        return sellerBytes.toString();
    }
    async getAllVehicles(ctx) {
        const iterator = await ctx.stub.getStateByPartialCompositeKey("vehicle", []);
        let travelOptions = [];
        while (true) {
            const res = await iterator.next();
            if (res.value && res.value.value.toString()) {
                travelOptions.push(JSON.parse(res.value.value.toString('utf8')));
            }
            if (res.done) {
                await iterator.close();
                break;
            }
        }
        return JSON.stringify(travelOptions);
    }


}

module.exports = TravelTicket;
