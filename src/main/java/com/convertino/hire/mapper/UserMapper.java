package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.UserGuestRequestDTO;
import com.convertino.hire.dto.request.UserRequestDTO;
import com.convertino.hire.dto.response.UserResponseDTO;
import com.convertino.hire.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @InheritInverseConfiguration
    UserResponseDTO mapToDTO(User user);

    // User mapToUser(UserResponseDTO userResponseDto);
    User mapToUser(UserRequestDTO userRequestDTO);
    User mapToUser(UserGuestRequestDTO userGuestRequestDTO);

    List<UserResponseDTO> mapToDTO(List<User> users);
}
