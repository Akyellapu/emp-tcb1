package com.qentelli.employeetrackingsystem.models.client.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class WeeklySummaryDto {
    private int weekId;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private List<String> upcomingTasks;
    private boolean status;
    private List<Integer> projectIds;
}