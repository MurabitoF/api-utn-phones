package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@type")
@JsonSubTypes(
        {@JsonSubTypes.Type(value = Client.class, name = "client"),
                @JsonSubTypes.Type(value = Employee.class, name = "employee")
        })
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "dni", length = 10, nullable = false, unique = true)
    private String dni;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @OneToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

//    public Long getAccountId() {
//        return accountId;
//    }
//
//    public void setAccountId(Long userId) {
//        this.accountId = userId;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getDni() {
//        return dni;
//    }
//
//    public void setDni(String dni) {
//        this.dni = dni;
//    }
//
//    public LocalDateTime getDeleteAt() {
//        return deleteAt;
//    }
//
//    public void setDeleteAt(LocalDateTime deleteAt) {
//        this.deleteAt = deleteAt;
//    }
//
//    public City getCity() {
//        return city;
//    }
//
//    public void setCity(City city) {
//        this.city = city;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
