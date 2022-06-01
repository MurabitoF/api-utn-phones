package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone_lines")
public class PhoneLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_line_id")
    private Long phoneLineId;

    @Column(name = "phone_number", length = 10, unique = true)
    private String phoneNumber;

}
