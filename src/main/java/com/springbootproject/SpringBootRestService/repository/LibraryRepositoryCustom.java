package com.springbootproject.SpringBootRestService.repository;

import com.springbootproject.SpringBootRestService.model.Library;

import java.util.List;

public interface LibraryRepositoryCustom {

    List<Library> findAllByAuthor(String author);
}
