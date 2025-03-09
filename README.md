# Store Billing Application



# Overview 
This is a Spring Boot application that calculates the total payable amount for a
bill after applying applicable discounts and converting the amount into a specified
currency using a third-party exchange rate API.

# Project Structure
store-billing-app
│── src
│   ├── main
│   │   ├── java/com/example/storebilling
│   │   │   ├── controller/BillingController.java
│   │   │   ├── service/BillingService.java
│   │   │   ├── service/CurrencyExchangeService.java
│   │   │   ├── util/BillingUtil.java
│   ├── resources
│   │   ├── application.properties
│── pom.xml
│── README.md


## Prerequisites
    * Java 17+
    * Maven
    * IntelliJ IDEA (or any Java IDE)


## Setup and Running the Application
   # Clone the repository:
     1. git clone https://github.com/abhinesh/store-billing-app.git
        cd store-billing-app

   # Build the project:
     2. mvn clean install

   # Run the application:
     3. mvn spring-boot:run

   # The server will start at: http://localhost:8084
   

## API Endpoints
  # Calculate Payable Amount
   
    ** Endpoint:
      POST /api/calculate

    ** Request Body Example:
        {
        "items": [
        {"name": "Laptop", "category": "electronics", "price": 1000},
        {"name": "Apple", "category": "grocery", "price": 5}
        ],
        "userType": "EMPLOYEE",
        "originalCurrency": "USD",
        "targetCurrency": "INR",
        "totalAmount": 1005
        }
     

    ** Response Example:
       {
        "convertedAmount": 75000,
        "targetCurrency": "INR"
        }
    

## Running Tests
     * Execute unit tests:
       mvn test

     * mvn testRun integration tests:
       mvn verify
  

## Integrated Third-Party API
  
     * This application integrates with the ExchangeRate API to fetch real-time currency conversion rates.

     1. Base URL: https://v6.exchangerate-api.com/v6
     2. API Key: Stored in application.properties



```Security
The application uses a secret key stored in application.properties for authentication and security purposes
```

## Author

## Developed Abhinesh Kumar Dubey.