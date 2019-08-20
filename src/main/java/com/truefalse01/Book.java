package com.truefalse01;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BOOKS")
public class Book {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
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
