package com.sappadev.simplewebangular.data.repositories;

import com.sappadev.simplewebangular.data.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Created by serge_000 on 31.10.2015.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.username = :username")
    /**
     * Find customer by username
     */
    Customer findCustomerByUsername(@Param("username") String username);
}
