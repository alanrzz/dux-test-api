package com.dux.software.futbol.api.services;

import static com.dux.software.futbol.api.enums.Role.USER;

import com.dux.software.futbol.api.dto.AuthResponseDto;
import com.dux.software.futbol.api.dto.LoginRequestDto;
import com.dux.software.futbol.api.dto.RegisterRequestDto;
import com.dux.software.futbol.api.entities.User;
import com.dux.software.futbol.api.exceptions.ResourceNotFoundException;
import com.dux.software.futbol.api.exceptions.UserNameAlreadyExistsException;
import com.dux.software.futbol.api.repositories.UserRepository;
import com.dux.software.futbol.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthResponseDto login(LoginRequestDto loginRequestDto) {
    log.debug("START login({})", loginRequestDto.getUsername());
    authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
        loginRequestDto.getUsername(), loginRequestDto.getPassword() ) );
    UserDetails userDetails = findOrFail( loginRequestDto.getUsername() );
    return buildResponse( userDetails );
  }

  public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
    log.debug("START register({})", registerRequestDto.getUsername());
    usernameAlreadyExists( registerRequestDto.getUsername() );
    User user = User.builder()
        .username( registerRequestDto.getUsername() )
        .password( passwordEncoder.encode( registerRequestDto.getPassword() ) )
        .role( USER )
        .build();
    userRepository.save( user );
    return buildResponse( user );
  }

  private AuthResponseDto buildResponse(UserDetails user) {
    log.debug("START buildResponse({})", user.getUsername());
    return new AuthResponseDto( jwtService.getToken( user ) );
  }

  private User findOrFail(String username) {
    log.debug("START findOrFail({})", username);
    return userRepository.findByUsername( username )
        .orElseThrow(() -> new ResourceNotFoundException( "No existe el usuario = " + username ));
  }

  private void usernameAlreadyExists(String username) {
    log.debug("START usernameAlreadyExists({})", username);
    if (userRepository.findByUsername( username ).isPresent()) {
      throw new UserNameAlreadyExistsException( "El nombre de usuario ya está en uso." );
    }
  }

}
