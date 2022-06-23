package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("client")
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends Account implements Serializable {

    @Column(name = "phone_number", length = 10, unique = true, nullable = false)
    public String phoneNumber;

    @OneToMany(mappedBy = "phoneOrigin")
    @JsonIgnore
    public List<Call> callsMade;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    public List<Bill> bills;

//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public List<Call> getCallsMade() {
//        return callsMade;
//    }
//
//    public void setCallsMade(List<Call> callsMade) {
//        this.callsMade = callsMade;
//    }
//
//    public List<Bill> getBills() {
//        return bills;
//    }
//
//    public void setBills(List<Bill> bills) {
//        this.bills = bills;
//    }
}
