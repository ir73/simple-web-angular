package com.sappadev.simplewebangular.data.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = "id")
/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 31.10.2015.
 */
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
}
