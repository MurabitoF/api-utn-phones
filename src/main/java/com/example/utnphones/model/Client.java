package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("client")
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends Account {

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
