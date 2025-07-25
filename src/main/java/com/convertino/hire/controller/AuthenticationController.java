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

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(AuthRoutes.REGISTER)
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) throws EntityDuplicateException, EntityCreationException {
        TokenResponseDTO tokenResponseDTO = authenticationService.register(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponseDTO);
    }

    @PostMapping(AuthRoutes.REGISTER_GUEST)
    public ResponseEntity<TokenResponseDTO> registerGuest(@Valid @RequestBody UserGuestRequestDTO userGuestRequestDTO) throws EntityCreationException {
        TokenResponseDTO tokenResponseDTO = authenticationService.register(userGuestRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponseDTO);
    }

    @PostMapping(AuthRoutes.LOGIN)
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) throws EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.authenticate(userLoginRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }

    @GetMapping(AuthRoutes.USER)
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        UserResponseDTO userResponseDTO = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok(userResponseDTO);
    }
}