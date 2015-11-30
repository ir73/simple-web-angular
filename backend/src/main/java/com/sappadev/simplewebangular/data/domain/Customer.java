package com.sappadev.simplewebangular.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMERS")
@Data
@EqualsAndHashCode(exclude = "id")
/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 31.10.2015.
 */
public class Customer implements Serializable {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "Lastname")
    private String lastName;

    @Column(name = "DateofBirth")
    private LocalDate dateOfBirth;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;
}
