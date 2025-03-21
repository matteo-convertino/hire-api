package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.JobPositionRequestDTO;
import com.convertino.hire.dto.request.UserGuestRequestDTO;
import com.convertino.hire.dto.request.UserRequestDTO;
import com.convertino.hire.dto.response.JobPositionResponseDTO;
import com.convertino.hire.dto.response.UserResponseDTO;
import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobPositionMapper {
    @InheritInverseConfiguration
    @Mapping(source = "user.id", target = "userId")
    JobPositionResponseDTO mapToDTO(JobPosition jobPosition);

    JobPosition mapToJobPosition(JobPositionRequestDTO jobPositionRequestDTO);

    List<JobPositionResponseDTO> mapToDTO(List<JobPosition> jobPositions);
}
