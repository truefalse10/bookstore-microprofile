package com.truefalse01;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 */
@ApplicationPath("resources")
@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"chief", "hacker"})
public class BookstoreApplication extends Application {

  @Inject
  BookRepository bookRepository;

  /**
   * utility method that fills database with test data. TODO: remove in production
   */
  @PostConstruct
  public void postConstruct() {
    System.out.println("Application started!");

    // init DB with some same books
    if (bookRepository.count() == 0) {
      bookRepository.create(new Book("Harry Potter and the philosophers stone", "J. K. Rowling"));
      bookRepository.create(new Book("Lord of the rings", "J. R. R. Tolkien"));
      bookRepository.create(new Book("Conan", "Robert E. Howard"));
      System.out.println("Added sample books!");
    }

  }

}
