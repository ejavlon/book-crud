package uz.ejavlon.app.mapper;

import uz.ejavlon.app.entity.Book;

import java.util.List;

public interface BookMapper {

//    @Select("select * from books")
    List<Book> getAllBooks();

    Book getBookById(long id);

    boolean existsById(long id);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(long id);
}