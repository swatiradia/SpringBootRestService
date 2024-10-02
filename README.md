# Spring Boot Library Management REST Service

This is a Spring Boot REST service for managing a library of books. It allows users to add, retrieve, update, and delete book records in a database. The service provides a simple API for managing book data, including features like checking for duplicates when adding new books.

## Features

- **Add Book**: Add a new book to the library. If a book with the same ISBN and aisle already exists, it skips the creation.
- **Get Book**: Retrieve a specific book by its unique ID.
- **Get Books by Author**: Retrieve a list of books by a specific author.
- **Update Book**: Update the details of an existing book.
- **Delete Book**: Remove a book from the library.
- **Get All Books**: Retrieve a list of all books in the library.

## Technologies Used

- **Java 11**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database (or any other database)**
- **Maven** for dependency management

## API Endpoints

### Add Book

POST /addBook

**Request Body:**

```http
{
  "isbn": "string",
  "aisle": "string",
  "author": "string",
  "book_name": "string"
} '''

**Response:**

Status 201 Created: Book added successfully.
Status 202 Accepted: Book already exists.

**Get Book**

GET /getBooks/{id}
Path Variable: id - The unique ID of the book.
Response: The book details.
Get Books by Author


GET /getBooks/author?authorName={authorName}
Query Parameter: authorName - The name of the author.
Response: List of books by the author.
Update Book


PUT /updateBook/{id}
Path Variable: id - The unique ID of the book to update.

Request Body:
{
  "aisle": "string",
  "author": "string",
  "book_name": "string"
}
Response: The updated book details.

Delete Book
http

DELETE /deleteBook
Request Body:

{
  "id": "string"
}
Response: Confirmation message on successful deletion.

Get All Books
http

GET /getAllBooks
Response: List of all books in the library.
Setup and Installation
Clone the repository:

git clone https://github.com/yourusername/spring-boot-library-management.git
cd spring-boot-library-management
Build the project using Maven:

mvn clean install
Run the application:

mvn spring-boot:run
Access the API at http://localhost:8080.

Logging
The application uses SLF4J for logging. Logs can be found in the console output.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or suggestions.
