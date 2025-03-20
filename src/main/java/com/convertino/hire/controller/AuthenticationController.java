package com.convertino.hire.controller;

import com.convertino.hire.dto.request.UserGuestRequestDTO;
import com.convertino.hire.dto.request.UserLoginRequestDTO;
import com.convertino.hire.dto.request.UserRequestDTO;
import com.convertino.hire.dto.response.TokenResponseDTO;
import com.convertino.hire.dto.response.UserResponseDTO;
import com.convertino.hire.exceptions.auth.InvalidCredentialsException;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityDuplicateException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.service.AuthenticationService;
import com.convertino.hire.utils.routes.AuthRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication-related requests.
 */
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Registers and authenticates a new customer user.
     *
     * @param userRequestDTO the user request data transfer object
     * @return the response entity containing the token response data transfer object
     * @throws EntityDuplicateException    if the entity already exists
     * @throws EntityCreationException     if there is an error creating the entity
     * @throws EntityNotFoundException     if the entity is not found
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    @PostMapping(AuthRoutes.REGISTER)
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) throws EntityDuplicateException, EntityCreationException,
            EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.register(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponseDTO);
    }

    @PostMapping(AuthRoutes.REGISTER_GUEST)
    public ResponseEntity<TokenResponseDTO> registerGuest(@Valid @RequestBody UserGuestRequestDTO userGuestRequestDTO) throws EntityDuplicateException, EntityCreationException,
            EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.register(userGuestRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponseDTO);
    }

    /**
     * Authenticates a user.
     *
     * @param userLoginRequestDTO the user login request data transfer object
     * @return the response entity containing the token response data transfer object
     * @throws EntityNotFoundException     if the entity is not found
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    @PostMapping(AuthRoutes.LOGIN)
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) throws EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.authenticate(userLoginRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }

    /**
     * Retrieves the authenticated user.
     *
     * @return the response entity containing the user response data transfer object
     */
    @GetMapping(AuthRoutes.USER)
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        UserResponseDTO userResponseDTO = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok(userResponseDTO);
    }
}