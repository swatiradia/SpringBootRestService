package com.springbootproject.SpringBootRestService.repository;

import com.springbootproject.SpringBootRestService.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository indicates that this interface is a Spring Data repository, which provides CRUD operations for the Library entity.
// It extends JpaRepository, giving access to methods for performing database operations like saving, deleting, and finding Library entities.
// The JpaRepository takes the entity class (Library) and the type of its primary key (String) as parameters.
@Repository
public interface LibraryRepository extends JpaRepository<Library, String >, LibraryRepositoryCustom {
}
