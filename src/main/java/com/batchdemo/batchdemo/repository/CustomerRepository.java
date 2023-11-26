package com.batchdemo.batchdemo.repository;

import com.batchdemo.batchdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
