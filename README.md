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

To create a book

```
curl --location 'localhost:8080/books' \
--header 'Content-Type: application/json' \
--header 'Authorization: ••••••' \
--data '{
    "isbn": "12334567",
    "format": "PAPERBACK",
    "title": "Song of Fire and Ice",
    "languageCode": "en_GB",
    "pageCount": 123,
    "quantity": 10,
    "price": 89.99,
    "publishedDate": "01.01.2005",
    "genreId": 1,
    "publisherId": 1,
    "authorIds": [1]
}'
```

To update a book

```
curl --location --request PUT 'localhost:8080/books' \
--header 'Content-Type: application/json' \
--header 'Authorization: ••••••' \
--data '{
    "id": 1,
    "title": "New title",
    "version": 0
}'
```

To delete a book

```
curl --location --request DELETE 'localhost:8080/books/1' \
--header 'Authorization: ••••••' \
```

#### Search

```
curl --location 'localhost:8080/books/search?page=0&size=2' \
--header 'Content-Type: application/json' \
--header 'Authorization: ••••••' \
--data '{
    "title": "song",
    "genreId": 6,
    "genreName": "fantasy",
    "authorId": 2,
    "authorName": "martin"
}'
```

#### Purchase

To buy books

```
curl --location 'localhost:8080/purchases/buy' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic ••••••' \
--data '[
    {
        "bookId": 1,
        "quantity": 2
    }
]'
```

To return books

```
curl --location 'localhost:8080/purchases/cancel' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic ••••••' \
--data '[
    {
        "bookId": 1,
        "quantity": 2
    }
]'
```

## TODO

- Introduce subgenres, when user searches by parent genre books of subgenre should be retrieved
- Link the same book of a different edition under book work, so you can get related books (earlier editions, different
  formats, etc.)
- Improve text search to compare unaccented values
- Improve Security, the current implementation is with mocked in memory users
- Improve purchase functionality to reserve items etc.