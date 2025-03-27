package com.convertino.hire.controller;

import com.convertino.hire.dto.request.InterviewRequestDTO;
import com.convertino.hire.dto.response.InterviewResponseDTO;
import com.convertino.hire.service.InterviewService;
import com.convertino.hire.utils.routes.InterviewRoutes;
import com.convertino.hire.utils.routes.SkillRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @GetMapping(InterviewRoutes.FIND_ALL_BY_USER)
    public ResponseEntity<List<InterviewResponseDTO>> findAllByUser() {
        return ResponseEntity.ok(interviewService.findAllByUser());
    }

    @GetMapping(InterviewRoutes.FIND_BY_ID)
    public ResponseEntity<InterviewResponseDTO> findById(@PathVariable long id) {
        return ResponseEntity.ok(interviewService.findById(id));
    }

    @GetMapping(InterviewRoutes.FIND_BY_JOB_POSITION_ID)
    public ResponseEntity<List<InterviewResponseDTO>> findAllByJobPositionId(@PathVariable long jobPositionId) {
        return ResponseEntity.ok(interviewService.findAllByJobPositionId(jobPositionId));
    }

    @PostMapping(InterviewRoutes.SAVE)
    public ResponseEntity<InterviewResponseDTO> save(@Valid @RequestBody InterviewRequestDTO interviewRequestDTO) {
        return ResponseEntity.ok(interviewService.save(interviewRequestDTO));
    }
}
