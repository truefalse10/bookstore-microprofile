package com.truefalse01;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/books")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
  private static final Logger log = Logger.getLogger(BookResource.class.getName());

  @Inject
  BookRepository bookRepository;

  @Inject
  JsonWebToken token;

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

  /**
   * create new book.
   * 
   * @return Book
   */
  @POST
  @RolesAllowed("hacker")
  public Book createBook(Book book) {
    bookRepository.create(book);
    log.info("New Book created: " + book);
    return book;
  }

}
