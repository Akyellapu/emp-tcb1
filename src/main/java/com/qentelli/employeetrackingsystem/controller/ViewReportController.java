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
import com.qentelli.employeetrackingsystem.models.client.request.ViewReportRequest;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.ViewReportResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.ViewReportService;

@RestController
@RequestMapping("/api/view-report")
public class ViewReportController {

	@Autowired
	private ViewReportService viewReportService;

	@PostMapping("/create")
	public ResponseEntity<AuthResponse<ViewReportResponse>> createReport(@RequestBody ViewReportRequest request) {
		ViewReportResponse response = viewReportService.saveReport(request);
		AuthResponse<ViewReportResponse> authResponse = new AuthResponse<>(
				HttpStatus.CREATED.value(),
				RequestProcessStatus.SUCCESS,
				"Report created successfully");
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthResponse<ViewReportResponse>> getReportById(@PathVariable Integer id) {
		ViewReportResponse response = viewReportService.getReportById(id);
		AuthResponse<ViewReportResponse> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Report fetched successfully",
				response);
		return ResponseEntity.ok(authResponse);
	}

	@GetMapping("/all")
	public ResponseEntity<AuthResponse<List<ViewReportResponse>>> getAllReports() {
		List<ViewReportResponse> responseList = viewReportService.getAllReports();
		AuthResponse<List<ViewReportResponse>> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"All reports fetched successfully",
				responseList);
		return ResponseEntity.ok(authResponse);
	}

	@PutMapping("/update")
	public ResponseEntity<AuthResponse<ViewReportResponse>> updateReport(@RequestBody ViewReportRequest request) {
		ViewReportResponse response = viewReportService.updateReport(request);
		AuthResponse<ViewReportResponse> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				"Report updated successfully"
				);
		return ResponseEntity.ok(authResponse);
	}
	
	// SOFT DELET
	@DeleteMapping("/softDeleteViewReport/{id}")
	public ResponseEntity<?> softDeleteViewReport(@PathVariable int id) {
	    viewReportService.softDeleteSummery(id);
	    AuthResponse<ViewReportResponse> response = new AuthResponse<>(
	            HttpStatus.OK.value(),
	            RequestProcessStatus.SUCCESS,
	            "ViewReport soft deleted successfully"
	    );

	    return ResponseEntity.ok(response);
	}

	 
	// HARD DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<AuthResponse<Void>> deleteReport(@PathVariable Integer id) {
		viewReportService.deleteReport(id);
		AuthResponse<Void> authResponse = new AuthResponse<>(
				HttpStatus.NO_CONTENT.value(),
				RequestProcessStatus.SUCCESS,
				"Report deleted successfully"
				);
		return new ResponseEntity<>(authResponse, HttpStatus.NO_CONTENT);
	}
}
