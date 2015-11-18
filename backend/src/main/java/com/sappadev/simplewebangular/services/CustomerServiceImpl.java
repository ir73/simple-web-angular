package com.sappadev.simplewebangular.services;

import com.sappadev.simplewebangular.data.converters.CustomerConverter;
import com.sappadev.simplewebangular.data.domain.Customer;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import com.sappadev.simplewebangular.data.repositories.CustomerRepository;
import com.sappadev.simplewebangular.security.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
/**
 * Created by serge_000 on 31.10.2015.
 */
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final CustomerConverter customerConverter;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerConverter.unconvert(customers);
    }

    @Override
    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        Customer c = customerConverter.convert(customer);
        Customer saved = customerRepository.saveAndFlush(c);
        return customerConverter.unconvert(saved);
    }

    @Override
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customer) {
        Customer c = customerConverter.convert(customer);
        Customer saved = customerRepository.save(c);
        return customerConverter.unconvert(saved);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading user by username {}", username);

        Customer customer = customerRepository.findCustomerByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_USER"));
        return new User(customer.getUsername(), customer.getPassword(), authorities);
    }

}
