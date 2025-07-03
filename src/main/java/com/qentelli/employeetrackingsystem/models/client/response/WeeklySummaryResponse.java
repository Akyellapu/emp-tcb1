package com.qentelli.employeetrackingsystem.models.client.response;

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
public class WeeklySummaryResponse {
    private int weekId;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private List<String> upcomingTasks;
    private List<String> projectNames;
    private String weekRange;

    public WeeklySummaryResponse(int weekId, String weekRange) {
        this.weekId = weekId;
        this.weekRange = weekRange;
    }
}