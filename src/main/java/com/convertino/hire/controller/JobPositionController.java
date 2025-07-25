package com.convertino.hire.controller;

import com.convertino.hire.dto.request.JobPositionRequestDTO;
import com.convertino.hire.dto.response.JobPositionResponseDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityDeletionException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.exceptions.entity.EntityUpdateException;
import com.convertino.hire.service.JobPositionService;
import com.convertino.hire.utils.routes.JobPositionRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @GetMapping(JobPositionRoutes.FIND_ALL_BY_USER)
    public ResponseEntity<List<JobPositionResponseDTO>> findAllByUser() {
        return ResponseEntity.ok(jobPositionService.findAllByUser());
    }

    @GetMapping(JobPositionRoutes.FIND_BY_ID)
    public ResponseEntity<JobPositionResponseDTO> findById(@PathVariable long id) throws EntityNotFoundException {
        return ResponseEntity.ok(jobPositionService.findById(id));
    }

    @PostMapping(JobPositionRoutes.SAVE)
    public ResponseEntity<JobPositionResponseDTO> save(@Valid @RequestBody JobPositionRequestDTO jobPositionRequestDTO) throws EntityCreationException {
        return ResponseEntity.ok(jobPositionService.save(jobPositionRequestDTO));
    }

    @PutMapping(JobPositionRoutes.UPDATE)
    public ResponseEntity<JobPositionResponseDTO> update(@PathVariable long id, @Valid @RequestBody JobPositionRequestDTO jobPositionRequestDTO) throws EntityNotFoundException, AccessDeniedException, EntityUpdateException {
        return ResponseEntity.ok(jobPositionService.update(id, jobPositionRequestDTO));
    }

    @DeleteMapping(JobPositionRoutes.DELETE)
    public ResponseEntity<JobPositionResponseDTO> delete(@PathVariable long id) throws EntityNotFoundException, AccessDeniedException, EntityDeletionException {
        return ResponseEntity.ok(jobPositionService.delete(id));
    }
}
