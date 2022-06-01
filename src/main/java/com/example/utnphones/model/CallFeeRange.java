package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calls_fees_ranges")
public class CallFeeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_fee_range_id")
    private Long callFeeRangeId;

    @Column(name = "start_at", nullable = false)
    private LocalTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_fee_id", nullable = false)
    private CallFee callFee;

    public Long getCallFeeRangeId() {
        return callFeeRangeId;
    }

    public void setCallFeeRangeId(Long callFeeRangeId) {
        this.callFeeRangeId = callFeeRangeId;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalTime endAt) {
        this.endAt = endAt;
    }

    public CallFee getCallFee() {
        return callFee;
    }

    public void setCallFee(CallFee callFee) {
        this.callFee = callFee;
    }
}
