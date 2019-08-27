package com.truefalse01;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKS")
public class Book {
  @Id
  @GeneratedValue(generator = "increment")
  public Long id;

  @Column
  public String title;

  @Column
  public String author;

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public Book() {
  }
}
