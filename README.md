## Light Logistics Warehouse - Spring Boot Application with Thymeleaf
This is a Spring Boot-based web application that allows users to manage inventory and warehouse items. The application uses Thymeleaf as the templating engine to render HTML pages dynamically.

Features
- Add new items to the warehouse catalog.
- Manage stock for each item with details like quantity and weight.
- Display items and stock on the homepage.
- Uses an H2 in-memory database to store item and stock data (no external database required).

Technologies Used
- Spring Boot: Java-based framework for building the backend.
- Thymeleaf: Templating engine for rendering HTML views.
- H2 Database: In-memory database for development and testing purposes.

Prerequisites
Before running this project, ensure you have the following installed on your system:

- Java 17+ (or a compatible version) – Required to run Spring Boot.
- Maven – For building the project.
- No external database required – The H2 database will be used as an in-memory database for development.

To run the application (in the `warehouse` directory)
1. Build the project using `mvn clean install`
2. Run the application using `mvn spring-boot:run`
3. The application will be available at http://localhost:8080
4. To run test suites (unit and integration tests): `mvn test`