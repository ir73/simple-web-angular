package com.sappadev.simplewebangular.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sappadev.simplewebangular.controllers.vo.ErrorCode;
import com.sappadev.simplewebangular.controllers.vo.ErrorResponseJson;
import com.sappadev.simplewebangular.controllers.vo.ResponseJson;
import com.sappadev.simplewebangular.controllers.vo.SuccessResponseJson;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import com.sappadev.simplewebangular.services.CustomerService;

@RestController
/**
 * A customer controller that handles CRUD operations for Customer domain model
 */
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/api/customers/", method = RequestMethod.GET)
    public List<CustomerDTO> getAllCustomers() {
        LOGGER.debug("Loading all customers...");
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/api/customers/{id}", method = RequestMethod.PUT)
    public ResponseJson saveCustomer(@Valid @RequestBody SaveCustomerRequestJson requestJson,
                                     BindingResult errors,
                                     @PathVariable("id") Long customerId) {
        LOGGER.info("Saving customer:{} / {}", customerId, requestJson);

        ResponseJson errorJson = handleError(errors);
        if (errorJson != null) {
            return errorJson;
        }

        if (!customerId.equals(requestJson.getId())) {
            return new ErrorResponseJson(ErrorCode.INVALID_INPUT);
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(requestJson.getDateOfBirth());
        customerDTO.setFirstName(requestJson.getFirstName());
        customerDTO.setId(requestJson.getId());
        customerDTO.setLastName(requestJson.getLastName());
        customerDTO.setPassword(requestJson.getPassword());
        customerDTO.setUsername(requestJson.getUsername());
        customerDTO = customerService.saveCustomer(customerDTO);

        SaveCustomerResponseJson json = new SaveCustomerResponseJson();
        json.customer = customerDTO;
        return json;
    }

    @RequestMapping(value = "/api/customers/", method = RequestMethod.POST)
    public ResponseJson createCustomer(@Valid @RequestBody CreateCustomerRequestJson requestJson,
                                     BindingResult errors) {
        LOGGER.info("Creating customer: {}", requestJson);

        ResponseJson errorJson = handleError(errors);
        if (errorJson != null) {
            return errorJson;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(requestJson.getDateOfBirth());
        customerDTO.setFirstName(requestJson.getFirstName());
        customerDTO.setLastName(requestJson.getLastName());
        customerDTO.setPassword(requestJson.getPassword());
        customerDTO.setUsername(requestJson.getUsername());
        customerDTO = customerService.createCustomer(customerDTO);

        SaveCustomerResponseJson json = new SaveCustomerResponseJson();
        json.customer = customerDTO;
        return json;
    }

    private ResponseJson handleError(BindingResult errors) {
        if (errors.hasErrors()) {
            SaveCustomerErrorJson saveCustomerErrorJson = new SaveCustomerErrorJson(ErrorCode.INVALID_INPUT);
            if (errors.hasFieldErrors()) {
                saveCustomerErrorJson.field = errors.getFieldError().getField();
            }
            return saveCustomerErrorJson;
        }
        return null;
    }

    @RequestMapping(value = "/api/customers/{id}", method = RequestMethod.DELETE)
    public ResponseJson deleteCustomer(@PathVariable("id") Long customerId) {
        LOGGER.info("Delete customer: {}", customerId);
        customerService.deleteCustomer(customerId);
        return new SuccessResponseJson();
    }

    @Data
    static class SaveCustomerRequestJson {
        @NotNull
        @Min(1)
        private Long id;

        @NotNull
        @Length(min = 1)
        private String firstName;

        @NotNull
        @Length(min = 1)
        private String lastName;

        @NotNull
        @Past
        private LocalDate dateOfBirth;

        @NotNull
        @Length(min = 1)
        private String username;

        @NotNull
        @Length(min = 1)
        private String password;
    }

    @Data
    static class CreateCustomerRequestJson {

        @NotNull
        @Length(min = 1)
        private String firstName;

        @NotNull
        @Length(min = 1)
        private String lastName;

        @NotNull
        @Past
        private LocalDate dateOfBirth;

        @NotNull
        @Length(min = 1)
        private String username;

        @NotNull
        @Length(min = 1)
        private String password;
    }

    @Data
    static class SaveCustomerResponseJson extends SuccessResponseJson {
        private CustomerDTO customer;
    }

    @Data
    static class SaveCustomerErrorJson extends ErrorResponseJson {
        private String field;
        public SaveCustomerErrorJson(ErrorCode errorCode) {
            super(errorCode);
        }
    }

}
