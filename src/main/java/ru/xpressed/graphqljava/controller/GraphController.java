package ru.xpressed.graphqljava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.xpressed.graphqljava.entity.Customer;
import ru.xpressed.graphqljava.entity.Product;
import ru.xpressed.graphqljava.repository.CustomerRepository;
import ru.xpressed.graphqljava.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class GraphController {
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    @QueryMapping
    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    @QueryMapping
    public List<Product> findProductsByCustomer(@Argument Integer customerID) {
        return productRepository.findByCustomer(customerRepository.findById(customerID).orElse(null));
    }

    record CustomerInput(String surname, String name, String patronymic) {}
    record ProductInput(String text, Integer qty, Boolean completed, Integer customerID) {}

    @MutationMapping
    public Customer addCustomer(@Argument CustomerInput customerInput) {
        return customerRepository.save(new Customer(customerInput.surname, customerInput.name, customerInput.patronymic));
    }

    @MutationMapping
    public Product addProduct(@Argument ProductInput productInput) {
        Customer customer = customerRepository.findById(productInput.customerID).orElse(null);
        Product product = new Product(productInput.text, productInput.qty, productInput.completed, customer);
        if (customer != null)
            customer.getProductList().add(product);
        return productRepository.save(product);
    }

    @MutationMapping
    public void deleteCustomer(@Argument Integer customerID) {
        customerRepository.deleteById(customerID);
    }

    @MutationMapping
    public void deleteProduct(@Argument Integer productID) {
        productRepository.deleteById(productID);
    }
}
