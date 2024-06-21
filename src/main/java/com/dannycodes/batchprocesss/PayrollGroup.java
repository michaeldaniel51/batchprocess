package com.dannycodes.batchprocesss;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Table(name = "payroll_group")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollGroup {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long employerId;
    private LocalDate period = LocalDate.now();
    private Integer status;
    private String name;











}
