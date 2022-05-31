package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("client")
@Table(name = "clients")
public class Client extends Account {

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @OneToOne
    @JoinColumn(name = "phone_line_id")
    private PhoneLine phoneLine;

    public PhoneLine getPhoneLine() {
        return phoneLine;
    }

    public void setPhoneLine(PhoneLine phoneLine) {
        this.phoneLine = phoneLine;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }
}
