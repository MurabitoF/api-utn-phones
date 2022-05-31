package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_id")
    private Long callId;

    @Column(name = "call_date")
    private LocalDateTime callDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "total")
    private Double total;

    @Column(name = "phone_origen")
    private String phoneOrigen;

    @Column(name = "phone_destination")
    private String PhoneDestination;

    @OneToOne
    @JoinColumn(name = "call_fee_id", insertable = false)
    private CallFee callFee;

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public LocalDateTime getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDateTime callDate) {
        this.callDate = callDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPhoneOrigen() {
        return phoneOrigen;
    }

    public void setPhoneOrigen(String phoneOrigen) {
        this.phoneOrigen = phoneOrigen;
    }

    public String getPhoneDestination() {
        return PhoneDestination;
    }

    public void setPhoneDestination(String phoneDestination) {
        PhoneDestination = phoneDestination;
    }

    public CallFee getCallFee() {
        return callFee;
    }

    public void setCallFee(CallFee callFee) {
        this.callFee = callFee;
    }
}
