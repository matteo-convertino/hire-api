package com.convertino.hire.controller;

import com.convertino.hire.dto.response.ReportResponseDTO;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.service.ReportService;
import com.convertino.hire.utils.routes.ReportRoutes;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(ReportRoutes.FIND_BY_ID)
    public ResponseEntity<ReportResponseDTO> findById(@PathVariable long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reportService.findById(id));
    }

    @GetMapping(ReportRoutes.FIND_BY_INTERVIEW_ID)
    public ResponseEntity<List<ReportResponseDTO>> findByAllByInterviewId(@PathVariable long interviewId) {
        return ResponseEntity.ok(reportService.findAllByInterviewId(interviewId));
    }

    @GetMapping(ReportRoutes.FIND_ALL_BY_USER)
    public ResponseEntity<List<ReportResponseDTO>> findByAllByUser() {
        return ResponseEntity.ok(reportService.findAllByUser());
    }

}
