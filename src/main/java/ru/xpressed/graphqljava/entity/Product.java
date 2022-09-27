package ru.xpressed.graphqljava.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;
    private int qty;
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Customer customer;

    public Product(String text, int qty, boolean completed, Customer customer) {
        this.text = text;
        this.qty = qty;
        this.completed = completed;
        this.customer = customer;
    }
}
