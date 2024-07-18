# `Hospital Management System`

This is a Hospital Management System built using Spring Boot and Spring Security. The system provides functionalities to manage patients, doctors, appointments, and other hospital-related activities. It also includes user authentication and authorization.

## Features

- User authentication and authorization
- JWT-based security
- Patient Management
- Doctor Management
- Appointment management
- Swagger UI for API documentation

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- MySQL (or any other preferred database)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ImCodeHub/hospital-management.git
   cd hospital-management-system
   
2. **Configure the database:**

    Update the `application.properties` file with your database configurations:

  ```properties
  spring.application.name=Hospital-Management
  # set up MYSQL database
  spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management
  spring.datasource.username=root
  spring.datasource.password=password
  
  spring.jpa.hibernate.ddl-auto=update
  logging.level.org.hibernate.SQL =DEBUG
  spring.jpa.show-sql=true
  server.port=8080
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
  
  # to check the security logs
  logging.level.org.springframework.security=TRACE
  
  # swagger api
  springdoc.api-docs.path=/v3/api-docs
  springdoc.swagger-ui.path=/swagger-ui.html
  ```
3. **Build and run the application:**

## API Documentation
The API documentation is available at: http://localhost:8080/swagger-ui.html

![image](https://github.com/ImCodeHub/PatientManagementSystem/assets/98458146/80ef6ddc-aff6-44fb-a32f-54777ca3735c)


## Project Structure
src/main/java/com/example/hospitalmanagement
`controllers` - REST controllers
`entity` - Entity classes
`models` - model classes
`repositories` - Spring Data JPA repositories
`services` - Service layer
`config` - Configuration files

## Authentication and Authorization
This project uses `JWT` for authentication and authorization. The following endpoints are available:

- Registration: POST `/api/auth/register`
- Login: POST `/api/auth/authenticate`

## Security Configuration
The security configuration is handled by SecurityConfiguration.java. It includes:

- CSRF protection disabled
- Session management policy set to STATELESS
- Permit all for /api/auth/** endpoints
- JWT authentication filter for other endpoints
- make user Blacklisted & whitelisted
  
## Contributing
Contributions are welcome! Please open an issue or submit a pull request.

---
# To contact me:
   - name: Ankit sharma
   - mobile no: 8962780856
   - E-mail id: ankitsharma.as420@gmail.com
   - My [Linked In](https://www.linkedin.com/in/ankit-sharma-a6689b1a5/) Profile.
     
**To see My other projects** [click here](https://github.com/ImCodeHub?tab=repositories)

