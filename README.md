# Bookstore Inventory Management Service

Service responsible for managing bookstore inventory.

## Features

- Book CRUD REST API (add/update/delete a book from inventory)
- Search REST API of books by criteria
- Data is stored in PostgreSQL
- Security - authn + authz

## Local Setup

### Requirements

- Java 21
- Maven 3.6.3+
- Postgres 17
- Docker (Optional)

### Docker

Use a supplied Docker Compose file to spin up Postgres or configure `application.yml` to point to your Postgres setup.

```
cd bookstore-inventory-management-service
docker compose -f docker-compose.yml up
```

To stop running Postgres, execute the following command.

```
docker compose -f docker-compose.yml down
```

### REST API

#### CRUD

#### Search

## TODO

- TBD