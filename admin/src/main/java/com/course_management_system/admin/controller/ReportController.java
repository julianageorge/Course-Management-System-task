package com.course_management_system.admin.controller;

import com.course_management_system.admin.dto.SystemReportResponse;
import com.course_management_system.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/summary")
    public SystemReportResponse summary() {
        return reportService.getSystemReport();
    }
}
