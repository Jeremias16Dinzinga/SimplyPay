# SimplyPay

## Project Overview
SimplyPay is a simplified payment platform that allows money transfers between users. The system supports two types of users: common users and merchants. Common users can send and receive money, while merchants can only receive payments. The platform ensures transactional integrity and includes external authorization and notification services.

## Technologies
* Spring Boot
* Spring DevTools
* Spring Web (RESTful API)
* Mockito (Testing)
* Hibernate (ORM)
* Spring Data JPA
* Swagger (API Documentation)
* H2 Database (In-Memory Database)
* SOLID Principles (Software Architecture)

## Requisites
* User registration with unique email and NIF/BI validation
* Money transfer between users, with restrictions for merchants
* Balance verification before processing a transfer
* External authorization service integration before completing a transaction
* Transaction rollback in case of inconsistency
* Notification service integration for transaction alerts
* RESTful API with proper HTTP status codes and responses
* API documentation using Swagger

## How to Use
1. Clone the project:
   ```sh
   git clone https://github.com/Jeremias16Dinzinga/SimplyPay.git
   ```
2. Navigate to the project directory:
   ```sh
   cd simplypay
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the API documentation via Swagger:
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Endpoints Example
* **POST /transfer** - Initiate a money transfer
  ```json
  {
    "amount": 100.0,
    "sender": 4,
    "receiver": 15
  }
  ```
* **POST /users** - Register a new user
  ```json
  {
    "fullName": "John Doe",
    "Nif": "324264130",
    "email": "jeremias@email.com",
    "password": "securePass123",
    "userType": "COMMON"
  }
  ```
* **GET /users/{id}** - Retrieve user details

## Actor
* [Jeremias Dinzinga](https://www.linkedin.com/in/jeremias-dinzinga-a9867b221/) - Backend Developer

