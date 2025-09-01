
# HappyTravel.com
This repo contains the code of our assignment of course CS731. The following steps must be taken to run this appliaction.

## Step1 - Install GO in your system (use official steps)

## Step2 - Install node and npm (latest versions)

## Step3 - Install Docker in your system

## Step4 - Install Maven in your system

## Step5 - Clone this repository

As a substep download a folder from this [Google Drive Link](URL)
. It containes essential jar file. 
You must unzip this folder and add the folder it contains in the folder devang/backend/login/registersp/ in the cloned repo.


## Step6 - To initialise backend -   
make sure you are inside folder devang   
```bash
cd backend/login/registersp/target
```
Then Run

```bash
java -jar fabric-client-0.0.1-SNAPSHOT.jar
```

## Step7 - For chaincode setup  
Again move to folder devang
```bash
./joinpeer.sh
```

## Step8 - For Frontend
To initialise the frontend  
### Step 8.1
```bash
cd frontend
cd api
```
### Step 8.2
Open axiosConfig.js and make sure you change the baseURL as -  
Instead of existing IP, put the IP that you get from IPconfig

Use the following commands to run the frontend
```bash
cd ..
npm -i
npm start
```



