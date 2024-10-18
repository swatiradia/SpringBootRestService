package com.springbootproject.SpringBootRestService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


// @Entity marks this class as a JPA entity that is mapped to a table in the database.
@Entity
// @Table specifies the name of the database table to which this entity is mapped.
@Table(name = "Storage1")
public class Library {

    // @Id indicates that this field is the primary key of the entity.
    @Id
    // @Column specifies the name of the database column for the primary key.
    @Column(name = "id")
    private String id;
    // @Column maps this field to a specific column in the database table.
    @Column(name = "book_name")
    private String book_name;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "aisle")
    private int aisle;
    @Column(name = "author")
    private String author;

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
