package com.example.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "province_name", length = 50, nullable = false)
    private String provinceName;

//    public Long getProvinceId() {
//        return provinceId;
//    }
//
//    public void setProvinceId(Long provinceId) {
//        this.provinceId = provinceId;
//    }
//
//    public String getProvinceName() {
//        return provinceName;
//    }
//
//    public void setProvinceName(String provinceName) {
//        this.provinceName = provinceName;
//    }
}
