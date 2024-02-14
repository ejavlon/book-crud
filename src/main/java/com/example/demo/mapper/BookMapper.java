package com.example.demo.mapper;

import com.example.demo.entity.Book;
import java.util.List;

public interface BookMapper {

    List<Book> getAllBooks();

    Book getBookById(long id);

    boolean existsById(long id);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(long id);
}
