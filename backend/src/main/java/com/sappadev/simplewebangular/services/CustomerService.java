package com.sappadev.simplewebangular.services;

import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * A service that allows making CRUD operations with the Customer object
 */
public interface CustomerService extends UserDetailsService {

	@PreAuthorize("hasRole('ROLE_USER')")
	/**
	 * Get list of all customers
	 *
	 * @return
	 */
	List<CustomerDTO> getAllCustomers();

	@PreAuthorize("hasRole('ROLE_USER')")
	/**
	 * Save new or update existing customer
	 *
	 * @param customer
	 * @return
	 */
	CustomerDTO saveCustomer(CustomerDTO customer);

	@PreAuthorize("hasRole('ROLE_USER')")
	/**
	 * Create a customer with ID == <code>customerId</code>
	 *
	 * @param customer
	 * @return
	 */
	CustomerDTO createCustomer(CustomerDTO customer);

	@PreAuthorize("hasRole('ROLE_USER')")
	/**
	 * Delete customer with id == <code>customerId</code>
	 *
	 * @param customerId
	 */
	void deleteCustomer(Long customerId);
}
