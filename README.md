# Stock Exchange Project

## Table of Contents

- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Database Access](#database-access)
- [Endpoints](#endpoints)
  - [POST Endpoints](#post-endpoints)
  - [GET Endpoints](#get-endpoints)
  - [DELETE Endpoints](#delete-endpoints)
  - [PUT Endpoints](#put-endpoints)
- [Usage](#usage)
- [Note](#note)

## About the Project

This project implements a Stock Exchange application that allows users to manage stocks and stock exchanges. Users can perform various operations such as adding, deleting, and updating stocks and stock exchanges.

## Tech Stack

- Java 11
- Maven
- Spring Boot 2.7.4
- H2 Database

## Installation

1. Open the project in your preferred integrated development environment (IDE) such as IntelliJ or Eclipse.
2. Once the project's dependencies are loaded, start the application.

## Database Access

You can access the H2 database using the following credentials:

- Driver Class: org.h2.Driver
- URL: jdbc:h2:mem:userdb
- Username: tarik
- Password: tarik

## Endpoints

The project provides various endpoints for managing stocks and stock exchanges.

### POST Endpoints

1. **Create a Stock**

   - Endpoint: `POST http://localhost:8080/api/stock`
   - Example JSON Body:
     
     {
         "name": "ING",
         "description": "Ing",
         "currentPrice": 100.00
     }
     

2. **Create a Stock Exchange**

   - Endpoint: `POST http://localhost:8080/api/stock-exchange`
   - Example JSON Body:
     
     {
         "name": "BINANCE",
         "description": "Binance"
     }
    

3. **Add Stock to Stock Exchange**

   - Endpoint: `POST http://localhost:8080/api/stock-exchange?stockName={stockName}&stockExchangeName={stockExchangeName}`

### GET Endpoints

1. **Retrieve All Stocks**

   - Endpoint: `GET http://localhost:8080/api/stock`

2. **Retrieve All Stocks in a Stock Exchange**

   - Endpoint: `GET http://localhost:8080/api/stock-exchange/{stockExchangeName}`

3. **Retrieve All Stock Exchanges**

   - Endpoint: `GET http://localhost:8080/api/stock-exchange`
   
	**On this endpoint, the liveInMarket field is set to true when Stock Exchange has 5 or more stocks. **

### DELETE Endpoints

1. **Delete Stock from a Stock Exchange**

   - Endpoint: `DELETE http://localhost:8080/api/stock-exchange/{stockExchangeName}/{stockName}`

2. **Delete Stock from system**

   - Endpoint: `DELETE http://localhost:8080/api/stock/{stockName}`

3. **Delete Stock Exchange from system**

   - Endpoint: `DELETE http://localhost:8080/api/stock-exchange/{stockExchangeName}`

### PUT Endpoints

1. **Update Current Price of a Stock**

   - Endpoint: `PUT http://localhost:8080/api/stock`
   - Example JSON Body:
     
     {
         "newPrice": 100.00,
         "stockName": "ING"
     }
    

## Usage

1. Use the provided endpoints to manage stocks and stock exchanges.
2. Perform CRUD (Create, Read, Update, Delete) operations as needed.
3. Access the H2 database at `/DB` with the given credentials for database management.

## Note

Please adjust the URLs and paths based on your deployment environment. This documentation assumes a local development setup on port 8080.
