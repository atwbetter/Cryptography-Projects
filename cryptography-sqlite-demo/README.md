# cryptography-sqlite-demo

A minimal Spring Boot 4.1 demo using SQLite (JdbcTemplate).

How to run:

1. cd cryptography-sqlite-demo
2. mvn -U clean package
3. mvn spring-boot:run

API endpoints:
- GET /users
- GET /users/{id}
- POST /users  {"name":"Alice","email":"alice@example.com"}
- PUT /users/{id}
- DELETE /users/{id}

The SQLite file database.db will be created in the project root when the app runs.
