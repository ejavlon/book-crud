package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import static jakarta.ws.rs.core.Response.Status.*;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {

//    private final BookService bookService;
//
//    @Inject
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

    private final BookService bookService = new BookService();

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
