package com.qentelli.employeetrackingsystem.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qentelli.employeetrackingsystem.entity.Person;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.entity.Task;
import com.qentelli.employeetrackingsystem.entity.ViewReports;
import com.qentelli.employeetrackingsystem.entity.WeeklySummary;
import com.qentelli.employeetrackingsystem.exception.ResourceNotFoundException;
import com.qentelli.employeetrackingsystem.models.client.request.ViewReportRequest;
import com.qentelli.employeetrackingsystem.models.client.response.ViewReportResponse;
import com.qentelli.employeetrackingsystem.repository.PersonRepository;
import com.qentelli.employeetrackingsystem.repository.ProjectRepository;
import com.qentelli.employeetrackingsystem.repository.ViewreportRepository;
import com.qentelli.employeetrackingsystem.repository.WeeklySummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewReportService {

	private static final String REPORT_NOT_FOUND = "Report not found";
	private static final String WEEKLY_SUMMARY_NOT_FOUND = "Weekly summary not found";
	private static final String PROJECT_NOT_FOUND = "Project not found";
	private static final String PERSON_NOT_FOUND = "Person not found";
	// private static final String USER_NOT_FOUND = "User not found";

	private ViewreportRepository viewReportRepository;

	private WeeklySummaryRepository weeklySummaryRepository;

	private ProjectRepository projectRepository;

//    @Autowired
//    private UserRepository userRepository;

	
	private PersonRepository personRepository;

	public ViewReportResponse saveReport(ViewReportRequest request) {
		WeeklySummary summary = weeklySummaryRepository.findById(request.getWeekId())
				.orElseThrow(() -> new RuntimeException(WEEKLY_SUMMARY_NOT_FOUND + " with id: " + request.getWeekId()));

		Project project = projectRepository.findById(request.getProjectId())
				.orElseThrow(() -> new RuntimeException(PROJECT_NOT_FOUND + " with id: " + request.getProjectId()));

//        User user = userRepository.findByEmployeeId(request.getEmployeeId())
//        	    .orElseThrow(() -> new RuntimeException("User not found"));

		Person person = personRepository.findByPersonId(request.getPersonId())
				.orElseThrow(() -> new RuntimeException(PERSON_NOT_FOUND + " with id: " + request.getPersonId()));

		ViewReports report = new ViewReports();
		report.setTaskName(request.getTaskName());
		report.setTaskStatus(request.getTaskStatus());
		report.setTaskStartDate(request.getTaskStartDate());
		report.setTaskEndDate(request.getTaskEndDate());
		report.setComments(request.getComments());
		report.setProject(project);
		// report.setUser(user);
		report.setPerson(person);
		report.setWeeklySummary(summary);
		report.setTask(new Task(request.getSummary(), request.getKeyAccomplishment()));

		ViewReports saved = viewReportRepository.save(report);

		ViewReportResponse response = new ViewReportResponse();
		response.setViewReportId(saved.getViewReportId());
		response.setTaskName(saved.getTaskName());
		response.setTaskStatus(saved.getTaskStatus());
		response.setSummary(saved.getTask().getSummary());
		response.setKeyAccomplishment(saved.getTask().getKeyAccomplishment());
		response.setComments(saved.getComments());
		response.setProjectName(saved.getProject().getProjectName());
		// response.setEmployeeName(saved.getUser().getFirstName() + " " +
		// saved.getUser().getLastName());
		response.setPersonName(saved.getPerson().getFirstName() + " " + saved.getPerson().getLastName());
		response.setTaskStartDate(saved.getTaskStartDate());
		response.setTaskEndDate(saved.getTaskEndDate());
		response.setCreatedAt(saved.getCreatedAt());
		response.setCreatedBy(saved.getCreatedBy());
		return response;
	}

	public ViewReportResponse updateReport(ViewReportRequest request) {
		ViewReports report = viewReportRepository.findById(request.getViewReportId())
				.orElseThrow(() -> new RuntimeException(REPORT_NOT_FOUND));

		WeeklySummary summary = weeklySummaryRepository.findById(request.getWeekId())
				.orElseThrow(() -> new RuntimeException(WEEKLY_SUMMARY_NOT_FOUND + " with id: " + request.getWeekId()));
		Project project = projectRepository.findById(request.getProjectId())
				.orElseThrow(() -> new RuntimeException(PROJECT_NOT_FOUND + " with id: " + request.getProjectId()));
//        User user = userRepository.findByEmployeeId(request.getEmployeeId())
//        	    .orElseThrow(() -> new RuntimeException("User not found"));

		Person person = personRepository.findByPersonId(request.getPersonId())
				.orElseThrow(() -> new RuntimeException(PERSON_NOT_FOUND + " with id: " + request.getPersonId()));

		report.setTaskName(request.getTaskName());
		report.setTaskStatus(request.getTaskStatus());
		report.setTaskStartDate(request.getTaskStartDate());
		report.setTaskEndDate(request.getTaskEndDate());
		report.setComments(request.getComments());
		report.setProject(project);
		// report.setUser(user);
		report.setPerson(person);
		report.setWeeklySummary(summary);
		report.setTask(new Task(request.getSummary(), request.getKeyAccomplishment()));

		ViewReports updated = viewReportRepository.save(report);

		ViewReportResponse response = new ViewReportResponse();
		response.setViewReportId(updated.getViewReportId());
		response.setTaskName(updated.getTaskName());
		response.setTaskStatus(updated.getTaskStatus());
		response.setSummary(updated.getTask().getSummary());
		response.setKeyAccomplishment(updated.getTask().getKeyAccomplishment());
		response.setComments(updated.getComments());
		response.setProjectName(updated.getProject().getProjectName());
		// response.setEmployeeName(updated.getUser().getFirstName() + " " +
		// updated.getUser().getLastName());
		response.setPersonName(updated.getPerson().getFirstName() + " " + updated.getPerson().getLastName());
		response.setTaskStartDate(updated.getTaskStartDate());
		response.setTaskEndDate(updated.getTaskEndDate());
		response.setCreatedAt(updated.getCreatedAt());
		response.setCreatedBy(updated.getCreatedBy());
		return response;
	}

	public ViewReportResponse getReportById(Integer id) {
		ViewReports report = viewReportRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(REPORT_NOT_FOUND + " with id: " + id));
		// Map to response (reuse mapping logic)
		ViewReportResponse response = new ViewReportResponse();
		response.setViewReportId(report.getViewReportId());
		response.setTaskName(report.getTaskName());
		response.setTaskStatus(report.getTaskStatus());
		response.setSummary(report.getTask().getSummary());
		response.setKeyAccomplishment(report.getTask().getKeyAccomplishment());
		response.setComments(report.getComments());
		response.setProjectName(report.getProject().getProjectName());
		// response.setEmployeeName(report.getUser().getFirstName() + " " +
		// report.getUser().getLastName());
		response.setPersonName(report.getPerson().getFirstName() + " " + report.getPerson().getLastName());
		response.setTaskStartDate(report.getTaskStartDate());
		response.setTaskEndDate(report.getTaskEndDate());
		response.setCreatedAt(report.getCreatedAt());
		response.setCreatedBy(report.getCreatedBy());
		return response;
	}

	public List<ViewReportResponse> getAllReports() {
		List<ViewReports> reports = viewReportRepository.findAll();
		List<ViewReportResponse> responses = new ArrayList<>();
		for (ViewReports report : reports) {
			ViewReportResponse response = new ViewReportResponse();
			response.setViewReportId(report.getViewReportId());
			response.setTaskName(report.getTaskName());
			response.setTaskStatus(report.getTaskStatus());
			response.setSummary(report.getTask().getSummary());
			response.setKeyAccomplishment(report.getTask().getKeyAccomplishment());
			response.setComments(report.getComments());
			response.setProjectName(report.getProject().getProjectName());
			// response.setEmployeeName(report.getUser().getFirstName() + " " +
			// report.getUser().getLastName());
			response.setPersonName(report.getPerson().getFirstName() + " " + report.getPerson().getLastName());
			response.setTaskStartDate(report.getTaskStartDate());
			response.setTaskEndDate(report.getTaskEndDate());
			response.setCreatedAt(report.getCreatedAt());
			response.setCreatedBy(report.getCreatedBy());
			responses.add(response);
		}
		return responses;
	}

	// SOFT DELETE
	public ViewReports softDeleteSummery(Integer viewReportId) {
		ViewReports viewReports = viewReportRepository.findById(viewReportId)
				.orElseThrow(() -> new ResourceNotFoundException("viewReportId not found"));
		viewReports.setSoftDelete(true);
		return viewReportRepository.save(viewReports);
	}

	public void deleteReport(Integer id) {
		ViewReports report = viewReportRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(REPORT_NOT_FOUND + " with id: " + id));
		viewReportRepository.delete(report);
	}

}
