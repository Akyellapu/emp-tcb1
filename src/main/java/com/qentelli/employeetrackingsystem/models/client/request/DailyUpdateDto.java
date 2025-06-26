package com.qentelli.employeetrackingsystem.models.client.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DailyUpdateDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String task;
    private String status;
    private List<String> keyAccomplishments;
    private String employeeId;
}