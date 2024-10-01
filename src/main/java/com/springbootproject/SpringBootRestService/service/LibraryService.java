package com.springbootproject.SpringBootRestService.service;
import com.springbootproject.SpringBootRestService.model.Library;
import com.springbootproject.SpringBootRestService.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service class responsible for managing library operations.
 * This class contains business logic related to books,
 * including adding, retrieving, and deleting books from the library.
 * It interacts with the LibraryRepository to perform data operations.
 */

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

//    Method to build id
    public String buildIdMethod(String isbn, int aisle){
        return isbn+aisle;
    }

//    Method to check if the book already exists
    public boolean checkBookAlreadyExists(String id){

// Attempt to retrieve a Library object by its ID from the repository.
// The return type is Optional<Library>, which helps to handle cases
// where the requested library may not exist in the database.
// This avoids null pointer exceptions and provides a more functional
// approach to dealing with potentially absent values.
        Optional<Library> lib = libraryRepository.findById(id);
        if(lib.isPresent()){
            return true;
        }else {
            return false;
        }

    }
}
