package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("client")
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends Account implements Serializable {

    @Column(name = "phone_number", length = 10, unique = true, nullable = false)
    public String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
