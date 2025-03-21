package com.convertino.hire.controller;

import com.convertino.hire.dto.request.JobPositionRequestDTO;
import com.convertino.hire.dto.response.JobPositionResponseDTO;
import com.convertino.hire.service.JobPositionService;
import com.convertino.hire.utils.routes.JobPositionRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class JobPositionController {
    private final JobPositionService jobPositionService;

    @GetMapping(JobPositionRoutes.FIND_ALL)
    public ResponseEntity<List<JobPositionResponseDTO>> findAll() {
        return ResponseEntity.ok(jobPositionService.findAll());
    }

    @GetMapping(JobPositionRoutes.FIND_BY_ID)
    public ResponseEntity<JobPositionResponseDTO> findById(@PathVariable long id) {
        return ResponseEntity.ok(jobPositionService.findById(id));
    }

    @PostMapping(JobPositionRoutes.SAVE)
    public ResponseEntity<JobPositionResponseDTO> save(@Valid @RequestBody JobPositionRequestDTO jobPositionRequestDTO) {
        return ResponseEntity.ok(jobPositionService.save(jobPositionRequestDTO));
    }

    @PutMapping(JobPositionRoutes.UPDATE)
    public ResponseEntity<JobPositionResponseDTO> update(@PathVariable long id, @Valid @RequestBody JobPositionRequestDTO jobPositionRequestDTO) {
        return ResponseEntity.ok(jobPositionService.update(id, jobPositionRequestDTO));
    }

    @DeleteMapping(JobPositionRoutes.DELETE)
    public ResponseEntity<JobPositionResponseDTO> delete(@PathVariable long id) {
        return ResponseEntity.ok(jobPositionService.delete(id));
    }
}
