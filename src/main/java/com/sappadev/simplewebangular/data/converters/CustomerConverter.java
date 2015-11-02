package com.sappadev.simplewebangular.data.converters;

import com.sappadev.simplewebangular.data.domain.Customer;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
/**
 * Created by serge_000 on 31.10.2015.
 */
public class CustomerConverter {

    private final Mapper mapper;

    @Autowired
    public CustomerConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Converts CustomerDTO to Customer domain model
     * @param customerDTO
     * @return
     */
    public Customer convert(CustomerDTO customerDTO) {
        return mapper.map(customerDTO, Customer.class);
    }

    /**
     * Converts Customer to CustomerDTO model
     * @param customer
     * @return
     */
    public CustomerDTO unconvert(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    /**
     * Converts list of Customer domain objects.
     * @param customers
     * @return
     */
    public List<CustomerDTO> unconvert(List<Customer> customers) {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        for (Customer customer : customers) {
            list.add(unconvert(customer));
        }
        return list;
    }
}
