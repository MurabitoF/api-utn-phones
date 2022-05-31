package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_org")
    private City cityOrg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_dest")
    private City cityDest;

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

    public City getCityOrg() {
        return cityOrg;
    }

    public void setCityOrg(City cityOrg) {
        this.cityOrg = cityOrg;
    }

    public City getCityDest() {
        return cityDest;
    }

    public void setCityDest(City cityDest) {
        this.cityDest = cityDest;
    }
}
