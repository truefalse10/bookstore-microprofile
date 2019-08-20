package com.truefalse01;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class BookRepository {

    @PersistenceContext
    EntityManager em;

    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    public Book exampleBook() {
        return new Book("Lord of the rings", "J. R. R. Tolkien");
    }

    @Transactional
    public void add(Book book) {
        em.persist(book);
    }

    public List<Book> findAll() {
        TypedQuery query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }
}
