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
import com.convertino.hire.utils.CookieUtils;
import com.convertino.hire.utils.routes.AuthRoutes;
import com.convertino.hire.utils.routes.WebAuthRoutes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class WebAuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(WebAuthRoutes.REGISTER)
    public ResponseEntity<Void> register(@Valid @RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response) throws EntityDuplicateException, EntityCreationException,
            EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.register(userRequestDTO);

        CookieUtils.setJwtCookie(response, tokenResponseDTO.getAccessToken());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(WebAuthRoutes.REGISTER_GUEST)
    public ResponseEntity<Void> registerGuest(@Valid @RequestBody UserGuestRequestDTO userGuestRequestDTO, HttpServletResponse response) throws EntityDuplicateException, EntityCreationException,
            EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.register(userGuestRequestDTO);

        CookieUtils.setJwtCookie(response, tokenResponseDTO.getAccessToken());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(WebAuthRoutes.LOGIN)
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse response) throws EntityNotFoundException, InvalidCredentialsException {
        TokenResponseDTO tokenResponseDTO = authenticationService.authenticate(userLoginRequestDTO);

        CookieUtils.setJwtCookie(response, tokenResponseDTO.getAccessToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping(WebAuthRoutes.LOGOUT)
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        CookieUtils.clearJwtCookie(response);

        return ResponseEntity.ok().build();
    }
}