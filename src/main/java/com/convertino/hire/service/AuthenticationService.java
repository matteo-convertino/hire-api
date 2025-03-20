package com.convertino.hire.service;


import com.convertino.hire.dto.request.UserGuestRequestDTO;
import com.convertino.hire.dto.request.UserRequestDTO;
import com.convertino.hire.dto.request.UserLoginRequestDTO;
import com.convertino.hire.dto.response.TokenResponseDTO;
import com.convertino.hire.dto.response.UserResponseDTO;

/**
 * Service interface for authentication-related operations.
 */
public interface AuthenticationService {

    /**
     * Registers and authenticates new user.
     *
     * @param userRequestDTO the user request data transfer object
     * @return the token response data transfer object
     */
    TokenResponseDTO register(UserRequestDTO userRequestDTO);
    TokenResponseDTO register(UserGuestRequestDTO userGuestRequestDTO);

    /**
     * Authenticates a user.
     *
     * @param userLoginRequestDTO the user login request data transfer object
     * @return the token response data transfer object
     */
    TokenResponseDTO authenticate(UserLoginRequestDTO userLoginRequestDTO);

    /**
     * Retrieves the authenticated user.
     *
     * @return the user response data transfer object
     */
    UserResponseDTO getAuthenticatedUser();
}