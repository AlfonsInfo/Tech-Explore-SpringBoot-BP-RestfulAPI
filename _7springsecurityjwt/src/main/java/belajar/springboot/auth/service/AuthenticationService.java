package belajar.springboot.auth.service;

import belajar.springboot.auth.dto.AuthenticateResponse;
import belajar.springboot.auth.dto.AuthenticationRequest;
import belajar.springboot.auth.dto.RegisterRequest;
import belajar.springboot.configuration.JwtService;
import belajar.springboot.user.Role;
import belajar.springboot.user.User;
import belajar.springboot.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticateResponse register(RegisterRequest request){
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);


       return AuthenticateResponse.builder()
               .token(jwtToken)
               .build();
    }

    public AuthenticateResponse authenticate(AuthenticationRequest request)
    {
        System.out.println("Authentication Manager");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        System.out.println("End Of Authentication Manager");

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }
}
