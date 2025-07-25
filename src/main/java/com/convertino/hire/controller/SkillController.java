package com.convertino.hire.controller;

import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityDeletionException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.exceptions.entity.EntityUpdateException;
import com.convertino.hire.service.SkillService;
import com.convertino.hire.utils.routes.SkillRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping(SkillRoutes.FIND_BY_ID)
    public ResponseEntity<SkillResponseDTO> findById(@PathVariable long id) throws EntityNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(skillService.findById(id));
    }

    @GetMapping(SkillRoutes.FIND_BY_JOB_POSITION_ID)
    public ResponseEntity<List<SkillResponseDTO>> findAllByJobPositionId(@PathVariable long jobPositionId) throws EntityNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(skillService.findAllByJobPositionId(jobPositionId));
    }

    @PostMapping(SkillRoutes.SAVE)
    public ResponseEntity<SkillResponseDTO> save(@Valid @RequestBody SkillRequestDTO skillRequestDTO) throws EntityNotFoundException, AccessDeniedException, EntityCreationException {
        return ResponseEntity.ok(skillService.save(skillRequestDTO));
    }

    @PutMapping(SkillRoutes.UPDATE)
    public ResponseEntity<SkillResponseDTO> update(@PathVariable long id, @Valid @RequestBody SkillUpdateRequestDTO skillUpdateRequestDTO) throws EntityNotFoundException, AccessDeniedException, EntityUpdateException {
        return ResponseEntity.ok(skillService.update(id, skillUpdateRequestDTO));
    }

    @DeleteMapping(SkillRoutes.DELETE)
    public ResponseEntity<SkillResponseDTO> delete(@PathVariable long id) throws EntityNotFoundException, AccessDeniedException, EntityDeletionException {
        return ResponseEntity.ok(skillService.delete(id));
    }
}
