package uz.ejavlon.app.service;

import uz.ejavlon.app.config.DatabaseManager;
import uz.ejavlon.app.dto.ApiResponse;
import uz.ejavlon.app.mapper.BookMapper;
import uz.ejavlon.app.entity.Book;
import jakarta.enterprise.inject.Model;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Objects;

@Model
public class BookService {

    public ApiResponse getAllBooks() {

        try (SqlSession sqlSession = DatabaseManager.getSqlSessionFactory().openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            List<Book> allBooks = bookMapper.getAllBooks();

            return new ApiResponse("All books",true,allBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ApiResponse getBookById(long id) {
        Book book = null;
        try (SqlSession sqlSession = DatabaseManager.getSqlSessionFactory().openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            if(bookMapper.existsById(id))
                book = bookMapper.getBookById(id);

        }catch (Exception e){
            e.printStackTrace();
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(book == null ? "Book not found" : "Book found");
        apiResponse.setData(book);
        apiResponse.setSuccess(Objects.nonNull(book));

        return apiResponse;
    }

    public boolean existsById(long id) {
        boolean have = false;
        try (SqlSession sqlSession = DatabaseManager.getSqlSessionFactory().openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            have = bookMapper.existsById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return have;
    }

    public ApiResponse addBook(Book book) {
        if (Objects.isNull(book))
            return new ApiResponse("Book is null", false,null);

        try (SqlSession sqlSession = DatabaseManager.getSqlSessionFactory().openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            bookMapper.addBook(book);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(book);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Successfully saved");

        return apiResponse;
    }

    public ApiResponse updateBook(long id,Book book) {
        ApiResponse apiResponse = getBookById(id);
        if (apiResponse.getData() == null)
            return apiResponse;

        Book updatedBook = (Book) apiResponse.getData();
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setTitle(book.getTitle());

        try (SqlSession sqlSession = DatabaseManager.getSqlSessionFactory().openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            bookMapper.updateBook(updatedBook);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ApiResponse("Book successfully updated",true,updatedBook);
    }

    public ApiResponse deleteBook(long id) {

        if (!existsById(id))
            return new ApiResponse("Book not found",false,null);

        try (SqlSession sqlSession = DatabaseManager.getSqlSessionFactory().openSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            bookMapper.deleteBook(id);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ApiResponse("Book successfully deleted",true,null);
    }
}