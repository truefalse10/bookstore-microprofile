package com.truefalse01;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.eclipse.microprofile.metrics.annotation.Gauge;

public class BookRepository {

  @PersistenceContext
  EntityManager em;

  @Gauge(unit = "books", name = "book_counter")
  public long count() {
    TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
    return query.getSingleResult();
  }

  @Transactional
  public void create(Book book) {
    em.persist(book);
  }

  public List<Book> findAll() {
    TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
    return query.getResultList();
  }

  public Book findById(Long id) {
    return em.find(Book.class, id);
  }

}
