

# Employee Tracking System

A Java-based web application built using Spring Boot for tracking employee details, status, and activity in an organization.

## 📦 Project Structure

EmployeeTracking_Main-main/
├── src/                      # Java source code  
├── .mvn/                     # Maven wrapper files  
├── pom.xml                   # Maven project configuration  
├── mvnw, mvnw.cmd            # Maven wrapper scripts  
├── HELP.md                   # Spring Boot help file  
├── README.md                 # Project documentation  
└── ...                       # Other files



## 🚀 Features

- Employee registration and profile management
- Attendance and time tracking
- Role-based access control
- Spring Boot MVC architecture
- REST API integration
- Maven for dependency management

## 🛠️ Technologies Used

- Java 8+
- Spring Boot
- Spring MVC
- Maven
- Postgres
- Git

### Prerequisites

- Java JDK 8 or higher
- Maven 3.6+
- POSTGRES
- IDE (Eclipse/STS/IntelliJ)

### Setup Instructions

1. **Clone the repository:**
   git clone https://github.com/your-username/EmployeeTracking_Main.git
   
   cd EmployeeTracking_Main

3. **Build the project:**
   mvn clean install
3.**Run the application:**
   mvn spring-boot:run
4.**Access the app:**
   http://localhost:8080

### Database Configuration
Update your application.properties or application.yml file inside 
src/main/resources/:

 spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
 
spring.datasource.username=root

spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update

### Testing
You can run tests using:
         mvn test




