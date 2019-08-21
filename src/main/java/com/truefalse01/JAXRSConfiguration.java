package com.truefalse01;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {

    @Inject
    BookRepository bookRepository;

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
