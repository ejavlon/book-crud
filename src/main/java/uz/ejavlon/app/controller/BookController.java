package uz.ejavlon.app.controller;

import uz.ejavlon.app.dto.ApiResponse;
import uz.ejavlon.app.entity.Book;
import uz.ejavlon.app.service.BookService;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Model
public class BookController {

    private final BookService bookService;

    @Inject
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    public Response getBooks() {
        return Response.ok(bookService.getAllBooks()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") long id) {
        ApiResponse apiResponse = bookService.getBookById(id);
        return Response.status(apiResponse.isSuccess() ? OK : NOT_FOUND).entity(apiResponse).build();
    }

    @POST
    public Response addBook(Book book) {
        ApiResponse apiResponse = bookService.addBook(book);
        return Response.status(apiResponse.isSuccess() ? CREATED : CONFLICT).entity(apiResponse).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") long id, Book book) {
        ApiResponse apiResponse = bookService.updateBook(id,book);
        return Response.status(apiResponse.isSuccess() ? OK : NOT_FOUND).entity(apiResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") long id) {
        ApiResponse apiResponse = bookService.deleteBook(id);
        return Response.status(apiResponse.isSuccess() ? NO_CONTENT : NOT_FOUND).entity(apiResponse).build();
    }

}