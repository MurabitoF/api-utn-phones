package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calls_fees")
public class CallFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_fee_id")
    private Long callFeeId;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "callFee")
    private List<CallFeeRange> callFeeRange;


    @OneToOne
    @JoinColumn(name = "city_origin")
    private City cityOrigin;


    @OneToOne
    @JoinColumn(name = "city_destination")
    private City cityDestination;

    public Long getCallFeeId() {
        return callFeeId;
    }

    public void setCallFeeId(Long callFeeId) {
        this.callFeeId = callFeeId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<CallFeeRange> getCallFeeRange() {
        return callFeeRange;
    }

    public void setCallFeeRange(List<CallFeeRange> callFeeRange) {
        this.callFeeRange = callFeeRange;
    }

    public City getCityOrigin() {
        return cityOrigin;
    }

    public void setCityOrigin(City cityOrigin) {
        this.cityOrigin = cityOrigin;
    }

    public City getCityDestination() {
        return cityDestination;
    }

    public void setCityDestination(City cityDestination) {
        this.cityDestination = cityDestination;
    }
}
