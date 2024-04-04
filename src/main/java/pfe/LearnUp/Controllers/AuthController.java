package pfe.LearnUp.Controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfe.LearnUp.Configurations.JwtTokenUtil;
import pfe.LearnUp.Dto.AuthRequest;
import pfe.LearnUp.Dto.AuthResponse;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Services.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;


    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(), request.getPassword()
                            )
                    );

            User user = (User) authenticate.getPrincipal();

            String token=jwtTokenUtil.generateToken(user.getUsername());
            AuthResponse authResponse=new AuthResponse();
            authResponse.setToken(token);
            User usr=userService.getUserByEmail(request.getEmail());
                authResponse.setUserId(usr.getUserId());


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse);

        }catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
