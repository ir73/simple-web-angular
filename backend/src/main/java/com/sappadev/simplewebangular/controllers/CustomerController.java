package com.sappadev.simplewebangular.controllers;

import com.sappadev.simplewebangular.controllers.vo.ErrorCode;
import com.sappadev.simplewebangular.controllers.vo.ErrorResponseJson;
import com.sappadev.simplewebangular.controllers.vo.ResponseJson;
import com.sappadev.simplewebangular.controllers.vo.SuccessResponseJson;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import com.sappadev.simplewebangular.services.CustomerService;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/customers/", method = RequestMethod.GET)
    public List<CustomerDTO> getAllCustomers() {
        LOGGER.debug("Loading all customers...");
        return customerService.getAllCustomers();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseJson saveCustomer(@Valid @RequestBody SaveCustomerRequestJson requestJson,
                                     BindingResult errors,
                                     @PathVariable("id") Long customerId) {
        LOGGER.info("Saving customer:{} / {}", customerId, requestJson);

        if (errors.hasErrors()) {
            SaveCustomerErrorJson saveCustomerErrorJson = new SaveCustomerErrorJson(ErrorCode.INVALID_INPUT);
            if (errors.hasFieldErrors()) {
                saveCustomerErrorJson.field = errors.getFieldError().getField();
            }
            return saveCustomerErrorJson;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/customers/", method = RequestMethod.POST)
    public ResponseJson createCustomer(@Valid @RequestBody CreateCustomerRequestJson requestJson,
                                     BindingResult errors) {
        LOGGER.info("Creating customer: {}", requestJson);

        if (errors.hasErrors()) {
            SaveCustomerErrorJson saveCustomerErrorJson = new SaveCustomerErrorJson(ErrorCode.INVALID_INPUT);
            if (errors.hasFieldErrors()) {
                saveCustomerErrorJson.field = errors.getFieldError().getField();
            }
            return saveCustomerErrorJson;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseJson deleteCustomer(@PathVariable("id") Long customerId) {
        LOGGER.info("Delete customer: {}", customerId);
        customerService.deleteCustomer(customerId);
        return new SuccessResponseJson();
    }

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
        private Date dateOfBirth;

        @NotNull
        @Length(min = 1)
        private String username;

        @NotNull
        @Length(min = 1)
        private String password;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "SaveCustomerRequestJson{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    static class CreateCustomerRequestJson {

        @NotNull
        @Length(min = 1)
        private String firstName;

        @NotNull
        @Length(min = 1)
        private String lastName;

        @NotNull
        @Past
        private Date dateOfBirth;

        @NotNull
        @Length(min = 1)
        private String username;

        @NotNull
        @Length(min = 1)
        private String password;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "CreateCustomerRequestJson{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    static class SaveCustomerResponseJson extends SuccessResponseJson {
        public CustomerDTO customer;
    }

    static class SaveCustomerErrorJson extends ErrorResponseJson {

        public String field;

        public SaveCustomerErrorJson(ErrorCode errorCode) {
            super(errorCode);
        }

    }

}
