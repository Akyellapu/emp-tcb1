package com.qentelli.employeetrackingsystem.models.client.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content; // The actual data items on the current page
    private int pageNumber; // The index of the current page (usually 0-based).
    private int pageSize; // How many items are returned per page
    private long totalElements; //Total number of records available across all pages
    private int totalPages; //Total number of pages available
    private boolean last; // Indicates if the current page is the last one. 
    					//Useful for disabling "Next" in pagination.
}