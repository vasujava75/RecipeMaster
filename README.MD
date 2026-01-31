# Recipe Master API

A RESTful API for managing recipes and ingredients built with Spring Boot, following CQRS (Command Query Responsibility Segregation) pattern.

## 🚀 Technologies Used

- **Java 21+** - Programming language
- **Spring Boot 4.x** - Application framework
- **Spring Data JPA** - Data persistence layer
- **Spring Web MVC** - RESTful web services
- **PostgreSQL** - Primary database
- **Flyway** - Database migration tool
- **Maven** - Build automation and dependency management
- **Lombok** - Reduces boilerplate code
- **MapStruct** - Java bean mapping--> due to some issue it is not used currently
- **SpringDoc OpenAPI** - API documentation
- **Spring Boot Validation** - Request validation
- **Testcontainers** - Integration testing with containers
- **Spring REST Docs** - API documentation generation
- **Probes** - Health and readiness checks
- **Accuator** - Application monitoring

## 📋 Features

- **Recipe Management**: Create, read, update, and delete recipes
- **Ingredient Management**: Manage ingredients associated with recipes
- **CQRS Pattern**: Separate controllers for commands and queries
- **Data Validation**: Comprehensive input validation
- **API Documentation**: Auto-generated OpenAPI/Swagger documentation
- **Database Migrations**: Version-controlled database schema changes
- **Integration Testing**: Containerized testing environment

## 🏗️ Architecture

The application follows CQRS pattern with separate controllers:

- **RecipeCommandController**: Handles CREATE, UPDATE, DELETE operations
- **RecipeQueryController**: Handles READ operations
- **DTOs**: Data transfer objects for API communication
- **Services**: Business logic layer
- **Entities**: JPA entities for database mapping

## 🚦 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Docker installed and running (for testing)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd RecipeMaster/RecipeMasterAPI

## Design patterns and Architecture

# Command Pattern (Implicit)
Create Command: createRecipe(RecipeDto)
Update Command: updateRecipe(Long id, RecipeDto)
Delete Command: deleteRecipe(Long id)
Each method encapsulates a specific operation


# Strategy Pattern
Different validation strategies can be implemented
Validation logic is separated and interchangeable


# Observer Pattern
Database per service pattern with PostgreSQL


## Testing 
- docker up and running in your local machine
- mvn clean install -P local

Which does following work
- Run Unit Tests
- Run Integration Tests with Testcontainers(H2 is not needed with this approach)
- Runs the application locally via docker compose.
- Internally it will up both postgress and application together
- Test using postman collection provided in the RecipeMasterConf/postman folder.



## Pending work

- Implement MapStruct for DTO to Entity mapping
- Openshift sandbox deployment via maven plugin
- Grafana and Prometheus integration for monitoring
- More comprehensive test cases
- CI/CD pipeline setup

