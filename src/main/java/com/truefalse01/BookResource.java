package com.truefalse01;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
public class BookResource {
    @Inject
    BookRepository bookRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @GET
    @Path("/example")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getExampleBook() {
        return bookRepository.exampleBook();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Long count() {
        return bookRepository.count();
    }
}
