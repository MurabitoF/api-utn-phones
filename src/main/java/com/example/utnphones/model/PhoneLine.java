package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "phone_lines")
public class PhoneLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_line_id")
    private Long phoneLineId;

    @Column(name = "phone_number", length = 10, unique = true)
    private String phoneNumber;

    public Long getPhoneLineId() {
        return phoneLineId;
    }

    public void setPhoneLineId(Long phoneLineId) {
        this.phoneLineId = phoneLineId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
