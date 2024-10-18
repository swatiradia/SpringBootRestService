package com.springbootproject.SpringBootRestService.controller;

import com.springbootproject.SpringBootRestService.entity.AddBookResponse;
import com.springbootproject.SpringBootRestService.entity.Library;
import com.springbootproject.SpringBootRestService.repository.LibraryRepository;
import com.springbootproject.SpringBootRestService.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    LibraryService libraryService;

// Declares a constant logger for this class, 'Logger' records log messages (info, errors, etc.)
// 'private' limits access to this class, 'static' ensures one logger instance is shared across all objects
// 'final' prevents reassignment of the logger
// 'LoggerFactory.getLogger()' creates a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);



// @PostMapping maps HTTP POST requests to this method.
    @PostMapping("/addBook")
    public ResponseEntity<AddBookResponse> addBook(@RequestBody Library library) {
        AddBookResponse addBookResponse = new AddBookResponse();
        String id = libraryService.buildIdMethod(library.getIsbn(), library.getAisle());
        if(!libraryService.checkBookAlreadyExists(id)){
            logger.info("Book does not exist so creating one");
            library.setId(id);
            libraryRepository.save(library);
            HttpHeaders headers = new HttpHeaders();
            headers.add("uniqueId", id);
            addBookResponse.setMessage("Success!! Book is Added");
            addBookResponse.setId(id);
// ResponseEntity represents the entire HTTP response, including status code, headers, and body.
// Here, it is returning a response with the body 'addBookResponse' and the HTTP status 'CREATED' (201).
            return new ResponseEntity<>(addBookResponse, headers, HttpStatus.CREATED);
        }else{
            logger.info("Book exists so skipping creation");
            addBookResponse.setMessage("Book already present!!");
            addBookResponse.setId(id);
            return new ResponseEntity<>(addBookResponse, HttpStatus.ACCEPTED);
        }
    }

// @GetMapping maps HTTP GET requests to this method.
    @GetMapping("/getBooks/{id}")
    public Library getBook(@PathVariable(value = "id") String id){
        try{
            return libraryRepository.findById(id).get();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

// @GetMapping maps HTTP GET requests to this method.
    @GetMapping("/getBooks/author")
    public List<Library> getBooksByAuthorName(@RequestParam(value = "authorName") String authorName){
        return libraryRepository.findAllByAuthor(authorName);
    }

//  @PutMapping maps HTTP PUT Request to this method.
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBook(@RequestBody Library library, @PathVariable(value = "id") String id) {
        Library existingBook = libraryService.getBookById(id);
        System.out.println(existingBook);
        existingBook.setAisle(library.getAisle());
        existingBook.setAuthor(library.getAuthor());
        existingBook.setBook_name(library.getBook_name());
//  To save in database
        libraryRepository.save(existingBook);
// Returning the JSON with new details and OK status code
        return new ResponseEntity<>(existingBook, HttpStatus.OK);
    }

//  @DeleteMapping maps HTTP DELETE Request to this method.
    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestBody Library library){
        try{
//            Library findBook = libraryRepository.findById(library.getId()).get();
            Library findBook = libraryService.getBookById(library.getId());
            libraryRepository.delete(findBook);
            logger.info("Book is Deleted");
            return new ResponseEntity<>("Book is Successfully deleted",HttpStatus.CREATED );
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Library>> getAllBooks(){
        List<Library> getAllTheBooks = libraryRepository.findAll();
        return new ResponseEntity<>(getAllTheBooks, HttpStatus.OK);
    }
}

