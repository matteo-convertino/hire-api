package com.convertino.hire.service.impl;

import com.convertino.hire.dto.request.UserGuestRequestDTO;
import com.convertino.hire.dto.request.UserLoginRequestDTO;
import com.convertino.hire.dto.request.UserRequestDTO;
import com.convertino.hire.dto.response.TokenResponseDTO;
import com.convertino.hire.dto.response.UserResponseDTO;
import com.convertino.hire.exceptions.auth.InvalidCredentialsException;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityDuplicateException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.mapper.UserMapper;
import com.convertino.hire.model.User;
import com.convertino.hire.repository.UserRepository;
import com.convertino.hire.security.JwtService;
import com.convertino.hire.service.AuthenticationService;
import com.convertino.hire.utils.Role;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of the {@link AuthenticationService} interface.
 * <p>
 * Provides methods for user registration, authentication, and password management.
 */
@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     * <p>
     * Registers a new customer user.
     *
     * @param userRequestDTO the user request data transfer object
     * @return the customer user response data transfer object
     * @throws EntityDuplicateException if the entity already exists
     * @throws EntityCreationException  if there is an error creating the entity
     */
    @Override
    public TokenResponseDTO register(UserRequestDTO userRequestDTO) {
        return registerModerator(userRequestDTO);
    }

    @Override
    public TokenResponseDTO register(UserGuestRequestDTO userGuestRequestDTO) {
        return registerGuest(userGuestRequestDTO);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Authenticates a user by email and password.
     *
     * @param userLoginRequestDTO the user login request data transfer object
     * @return the token response data transfer object
     * @throws InvalidCredentialsException if the credentials are invalid
     * @throws EntityNotFoundException     if the entity is not found
     */
    @Override
    public TokenResponseDTO authenticate(UserLoginRequestDTO userLoginRequestDTO) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        try {
            authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findModeratorByEmail(userLoginRequestDTO.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("user", "email", userLoginRequestDTO.getEmail()));

        String accessToken = jwtService.generateToken(user);

        return new TokenResponseDTO(accessToken);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves the authenticated user.
     *
     * @return the user response data transfer object
     */
    @Override
    public UserResponseDTO getAuthenticatedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.mapToDTO(user);
    }

    public TokenResponseDTO registerModerator(UserRequestDTO userRequestDTO) {
        User user = userRepository.findModeratorByEmail(userRequestDTO.getEmail()).orElse(null);

        if (user != null)
            throw new EntityDuplicateException("user", "email", userRequestDTO.getEmail());

        user = userMapper.mapToUser(userRequestDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.MODERATOR);

        return register(user);
    }

    public TokenResponseDTO registerGuest(UserGuestRequestDTO userGuestRequestDTO) {
        User user = userMapper.mapToUser(userGuestRequestDTO);

        user.setRole(Role.GUEST);
        user.setUuid(UUID.randomUUID());

        return register(user);
    }

    public TokenResponseDTO register(User user) {
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new EntityCreationException("user");
        }

        String accessToken = jwtService.generateToken(user);

        return new TokenResponseDTO(accessToken);
    }
}