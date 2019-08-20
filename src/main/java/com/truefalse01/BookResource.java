package com.truefalse01;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books")
public class BookResource {
    @Inject
    BookRepository bookRepository;

    @GET
    @Path("/example")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getExampleBook() {
        return bookRepository.exampleBook();
    }
}
