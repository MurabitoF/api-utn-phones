package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("client")
@Table(name = "clients")
public class Client extends User {

    @OneToOne
    @JoinColumn(name = "phone_line_id")
    private PhoneLine phoneLine;

    public PhoneLine getPhoneLine() {
        return phoneLine;
    }

    public void setPhoneLine(PhoneLine phoneLine) {
        this.phoneLine = phoneLine;
    }
}
