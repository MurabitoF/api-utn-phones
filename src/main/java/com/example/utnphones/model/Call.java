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

    @Column(name = "call_date", nullable = false)
    private LocalDateTime callDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "total", insertable = false, nullable = false)
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_origin", referencedColumnName = "phone_number", nullable = false)
    private PhoneLine phoneOrigin;


    @ManyToOne
    @JoinColumn(name = "phone_destination", referencedColumnName = "phone_number", nullable = false)
    private PhoneLine phoneDestination;

    @OneToOne
    @JoinColumn(name = "call_fee_id", insertable = false)
    private CallFee callFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    public Call(LocalDateTime callDate, Integer duration, PhoneLine phoneOrigin, PhoneLine phoneDestination) {
        this.callDate = callDate;
        this.duration = duration;
        this.phoneOrigin = phoneOrigin;
        this.phoneDestination = phoneDestination;
    }

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

    public PhoneLine getPhoneOrigin() {
        return phoneOrigin;
    }

    public void setPhoneOrigen(PhoneLine phoneOrigin) {
        this.phoneOrigin = phoneOrigin;
    }

    public PhoneLine getPhoneDestination() {
        return phoneDestination;
    }

    public void setPhoneDestination(PhoneLine phoneDestination) {
        this.phoneDestination = phoneDestination;
    }

    public CallFee getCallFee() {
        return callFee;
    }

    public void setCallFee(CallFee callFee) {
        this.callFee = callFee;
    }

    public void setPhoneOrigin(PhoneLine phoneOrigin) {
        this.phoneOrigin = phoneOrigin;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
