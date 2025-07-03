package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.WeeklySummaryRequest;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.WeeklySummaryResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.WeeklySummaryService;

@RestController
@RequestMapping("/api/weekly-summary")
public class WeeklySummaryController {

	@Autowired
	private WeeklySummaryService weeklySummaryService;

	@PostMapping("/create-summary")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> createWeeklySummary(
			@RequestBody WeeklySummaryRequest request) {

		WeeklySummaryResponse response = weeklySummaryService.createSummary(request);
		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(HttpStatus.CREATED.value(),
				RequestProcessStatus.SUCCESS, "Weekly summary created successfully");

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@GetMapping("/{weekId}")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> getWeeklySummary(@PathVariable Integer weekId) {
		WeeklySummaryResponse response = weeklySummaryService.getSummaryById(weekId);
		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, LocalDateTime.now(), "Weekly summary fetched successfully", response);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<AuthResponse<List<WeeklySummaryResponse>>> getAllWeeklySummaries() {
		List<WeeklySummaryResponse> responseList = weeklySummaryService.getAllSummaries();
		System.out.println("Weekly summaries found: " + responseList.size());
		AuthResponse<List<WeeklySummaryResponse>> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, 
				LocalDateTime.now(), 
				"All weekly summaries fetched successfully",
				responseList);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@PutMapping("/update-summary")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> updateWeeklySummary(
			@RequestBody WeeklySummaryRequest request) {
		WeeklySummaryResponse response = weeklySummaryService.updateSummary(request);
		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "Weekly summary updated successfully");
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@DeleteMapping("/softDeleteSummary/{weekId}")
	public ResponseEntity<?> softDeleteAccount(@PathVariable int weekId) {
		weeklySummaryService.softDeleteSummery(weekId);

		AuthResponse<WeeklySummaryRequest> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "WeeklySummary soft deleted successfully");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{weekId}")
	public ResponseEntity<AuthResponse<Void>> deleteWeeklySummary(@PathVariable Integer weekId) {
		weeklySummaryService.deleteSummary(weekId);
		AuthResponse<Void> authResponse = new AuthResponse<>(HttpStatus.NO_CONTENT.value(),
				RequestProcessStatus.SUCCESS, "Weekly summary deleted successfully"

		);
		return new ResponseEntity<>(authResponse, HttpStatus.NO_CONTENT);
	}
	@GetMapping("/week-ranges")
	public ResponseEntity<AuthResponse<List<WeeklySummaryResponse>>> getWeekRanges() {
		List<WeeklySummaryResponse> responseList = weeklySummaryService.getFormattedWeekRanges();
		AuthResponse<List<WeeklySummaryResponse>> authResponse = new AuthResponse<>(
		        HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, 
				LocalDateTime.now(), 
				"Weekly summary week ranges fetched successfully",
				responseList);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}	
}
