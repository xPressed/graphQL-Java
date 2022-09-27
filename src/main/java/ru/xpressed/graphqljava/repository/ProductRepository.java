package ru.xpressed.graphqljava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpressed.graphqljava.entity.Customer;
import ru.xpressed.graphqljava.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCustomer(Customer customer);
    void deleteByCustomer(Customer customer);
}
