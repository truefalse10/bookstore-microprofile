package com.truefalse01;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
  @Inject
  BookRepository bookRepository;

  @GET
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @GET
  @Path("/count")
  @Produces(MediaType.TEXT_PLAIN)
  public Long count() {
    return bookRepository.count();
  }

  @GET
  @Path("/{id}")
  public Book findById(@PathParam("id") Long id) {
    return bookRepository.findById(id);
  }

  @POST
  public Book createBook(Book book) {
    bookRepository.create(book);
    return book;
  }

}
