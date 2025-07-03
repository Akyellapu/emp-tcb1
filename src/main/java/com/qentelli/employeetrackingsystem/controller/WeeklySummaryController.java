package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.qentelli.employeetrackingsystem.entity.WeeklySummary;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.WeeklySummaryRequest;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.WeeklySummaryResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.WeeklySummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weekly-summary")
public class WeeklySummaryController {

	private final WeeklySummaryService weeklySummaryService;

	@PostMapping("/create")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> createWeeklySummary(
			@RequestBody WeeklySummaryRequest request) {
		
		WeeklySummaryResponse response = weeklySummaryService.createSummary(request);
		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(
				HttpStatus.CREATED.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Weekly summary created successfully",
				response
		);

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@GetMapping("/{weekId}")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> getWeeklySummary(@PathVariable Integer weekId) {
		WeeklySummaryResponse response = weeklySummaryService.getSummaryById(weekId);
		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Weekly summary fetched successfully",
				response
		);
		return ResponseEntity.ok(authResponse);
	}

	@GetMapping("/all")
	public ResponseEntity<AuthResponse<List<WeeklySummaryResponse>>> getAllWeeklySummaries() {
		List<WeeklySummaryResponse> summaries = weeklySummaryService.getAllSummaries();
		AuthResponse<List<WeeklySummaryResponse>> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"All weekly summaries fetched successfully",
				summaries
		);
		return ResponseEntity.ok(authResponse);
	}

	@PutMapping("/update")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> updateWeeklySummary(
			@RequestBody WeeklySummaryRequest request) {
		
		WeeklySummaryResponse response = weeklySummaryService.updateSummary(request);
		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Weekly summary updated successfully",
				response
		);
		return ResponseEntity.ok(authResponse);
	}

	@DeleteMapping("/soft-delete/{weekId}")
	public ResponseEntity<AuthResponse<WeeklySummaryResponse>> softDeleteWeeklySummary(@PathVariable Integer weekId) {
		WeeklySummary softDeleted = weeklySummaryService.softDeleteSummery(weekId);

		WeeklySummaryResponse response = new WeeklySummaryResponse();
		response.setWeekId(softDeleted.getWeekId());
		response.setWeekStartDate(softDeleted.getWeekStartDate());
		response.setWeekEndDate(softDeleted.getWeekEndDate());
		response.setUpcomingTasks(softDeleted.getUpcomingTasks());

		AuthResponse<WeeklySummaryResponse> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Weekly summary soft deleted successfully",
				response
		);
		return ResponseEntity.ok(authResponse);
	}

	@DeleteMapping("/delete/{weekId}")
	public ResponseEntity<AuthResponse<Void>> deleteWeeklySummary(@PathVariable Integer weekId) {
		weeklySummaryService.deleteSummary(weekId);

		AuthResponse<Void> authResponse = new AuthResponse<>(
				HttpStatus.NO_CONTENT.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Weekly summary deleted successfully",
				null
		);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(authResponse);
	}

	@GetMapping("/week-ranges")
	public ResponseEntity<AuthResponse<List<WeeklySummaryResponse>>> getWeekRanges() {
		List<WeeklySummaryResponse> weekRanges = weeklySummaryService.getFormattedWeekRanges();
		AuthResponse<List<WeeklySummaryResponse>> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Weekly summary week ranges fetched successfully",
				weekRanges
		);
		return ResponseEntity.ok(authResponse);
	}
}