package com.springbootproject.SpringBootRestService.repository;

import com.springbootproject.SpringBootRestService.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryRepositoryImpl implements LibraryRepositoryCustom {

    // The LibraryRepository interface is automatically injected using @Autowired
    // This allows Spring to provide an implementation of LibraryRepository at runtime.

    @Autowired
    LibraryRepository libraryRepository;

    // The @Override annotation ensures that the method below implements the method
    // declared in the LibraryRepositoryCustom interface. This method is used to find
    // all books written by a specific author.
    @Override
    public List<Library> findAllByAuthor(String authorName) {
        // Fetches all books from the library repository (database).
        List<Library> books = libraryRepository.findAll();

        // A list to store books by the specified author.
        List<Library> authorNames = new ArrayList<Library>();

        // Iterates over the list of books to check if the author matches the specified authorName.
        for (Library item : books) {
            // If the author of the book matches (case-insensitive), add the book to the authorNames list.
            if (item.getAuthor().equalsIgnoreCase(authorName)) {
                authorNames.add(item);
            }
        }

        // Return the list of books that match the specified author's name.
        return authorNames;
    }
}
