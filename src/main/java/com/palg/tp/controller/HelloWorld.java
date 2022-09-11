package com.palg.tp.controller;

import com.palg.tp.todelete.Customer;
import com.palg.tp.todelete.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorld {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world.";
    }

    @GetMapping("/customers")
    public Iterable<Customer> customers(){
        return customerRepository.findAll();
    }
}