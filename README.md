# User Management API

A comprehensive RESTful API for user management built with Spring Boot 3.4.1, featuring JWT authentication, caching, and comprehensive testing.

## 🚀 Features

- **CRUD Operations**: Complete user management with Create, Read, Update, Delete operations
- **JWT Authentication**: Secure API with JSON Web Token-based authentication
- **Caching**: Improved performance with Spring Cache abstraction
- **Data Validation**: Comprehensive input validation using Bean Validation
- **Error Handling**: Global exception handling with structured error responses
- **API Documentation**: Interactive Swagger UI documentation
- **Database Migration**: Liquibase for database schema versioning
- **Testing**: Unit and integration tests with high coverage
- **Monitoring**: Actuator endpoints for health checks and metrics

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.4.1**
- **Spring Security 6** (JWT Authentication)
- **Spring Data JPA** (Data Access Layer)
- **Spring Cache** (Caching Abstraction)
- **PostgreSQL** (Production Database)
- **H2 Database** (Testing)
- **Liquibase** (Database Migration)
- **Lombok** (Boilerplate Code Reduction)
- **OpenAPI 3/Swagger** (API Documentation)
- **JUnit 5** (Testing Framework)
- **Mockito** (Mocking Framework)
- **Maven** (Build Tool)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 13+ (for production)
- Git

## 🔧 Setup & Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd user-management-api
```

### 2. Database Setup
```bash
# Create PostgreSQL database
createdb testdb

# Update database credentials in application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testdb
    username: your_username
    password: your_password
```

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔐 Authentication

The API uses JWT-based authentication. Two default users are available:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |

### Getting JWT Token
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

### Using JWT Token
```bash
curl -X GET http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer <your-jwt-token>"
```

## 📖 API Endpoints

### Authentication Endpoints
- `POST /api/v1/auth/login` - User authentication
- `POST /api/v1/auth/validate` - Token validation

### User Management Endpoints
- `GET /api/v1/users` - Get all users (paginated)
- `GET /api/v1/users/{id}` - Get user by ID
- `POST /api/v1/users` - Create new user
- `PUT /api/v1/users/{id}` - Update existing user
- `DELETE /api/v1/users/{id}` - Delete user

### Monitoring Endpoints
- `GET /actuator/health` - Health check
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## 📚 API Documentation

Interactive API documentation is available at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserServiceTest
```

### Generate Test Coverage Report
```bash
mvn jacoco:report
```

## 📊 Caching Strategy

The application implements caching for improved performance:

- **Cache Provider**: ConcurrentMapCacheManager (in-memory)
- **Cached Operations**: 
  - `getUserById()` - Caches individual user lookups
- **Cache Eviction**: 
  - Automatic eviction on user updates and deletions
- **Cache Configuration**: TTL set to 1 hour

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    address VARCHAR(200) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Database Migration
Database schema is managed using Liquibase:
- Migration files: `src/main/resources/db/changelog/`
- Master changelog: `db.changelog-master.json`

## 🔒 Security Features

- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **Password Encoding**: BCrypt password hashing
- **Role-Based Access**: ADMIN and USER roles
- **CORS Configuration**: Cross-origin resource sharing support
- **Security Headers**: Proper HTTP security headers
- **Input Validation**: Comprehensive request validation

## 📋 Configuration

### Application Properties
Key configuration properties in `application.yml`:

```yaml
jwt:
  secret: your-secret-key
  expiration: 86400000  # 24 hours

cache:
  ttl: 3600  # 1 hour

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testdb
    username: postgres
    password: your-password
```

### Environment Variables
You can override configuration using environment variables:
- `DB_URL` - Database URL
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `JWT_SECRET` - JWT secret key

## 🚀 Deployment

### Docker Deployment
```bash
# Build image
docker build -t user-management-api .

# Run container
docker run -p 8080:8080 user-management-api
```

### Production Considerations
- Use external cache provider (Redis/Hazelcast)
- Configure proper logging levels
- Set up monitoring and alerting
- Use environment-specific configurations
- Implement rate limiting
- Add request/response logging

## 📈 Performance Optimization

- **Connection Pooling**: HikariCP for database connections
- **Caching**: Strategic caching of frequently accessed data
- **Pagination**: Efficient data retrieval with pagination
- **Database Indexes**: Optimized database queries
- **Query Optimization**: Efficient JPA queries

## 🔍 Monitoring & Observability

### Health Checks
- Database connectivity
- Application status
- Custom health indicators

### Metrics
- Request count and timing
- Cache hit/miss ratios
- Database connection pool metrics
- JVM metrics

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify PostgreSQL is running
   - Check database credentials in application.yml
   - Ensure database exists

2. **JWT Token Invalid**
   - Check token expiration
   - Verify JWT secret configuration
   - Ensure proper Authorization header format

3. **Tests Failing**
   - Run `mvn clean test` to ensure clean state
   - Check test database configuration
   - Verify test data setup

### Debug Mode
Enable debug logging:
```yaml
logging:
  level:
    com.user.management: DEBUG
```

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Built with ❤️ using Spring Boot**