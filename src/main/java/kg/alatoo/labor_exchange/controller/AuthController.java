package kg.alatoo.labor_exchange.controller;

import kg.alatoo.labor_exchange.payload.request.StudentRequest;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import kg.alatoo.labor_exchange.service.EmailService;
import kg.alatoo.labor_exchange.service.StudentService;
import kg.alatoo.labor_exchange.service.TutorService;
import kg.alatoo.labor_exchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final TutorService tutorService;
    private final StudentService studentService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority> )userDetails.getAuthorities();
        String accessToken = jwtUtil.generateToken(authorities, userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(authorities,userDetails.getUsername());

        Map<String,String> response = new HashMap<>();
        response.put("access_token", accessToken);
        response.put("refresh_token", refreshToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,String>> refreshToken(@RequestParam String refreshToken) {
        Map<String,String> response = jwtUtil.refresh(refreshToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register/tutor")
    public ResponseEntity<String> register(@RequestBody TutorRequest request,
                                           @RequestParam(required = false) MultipartFile profilePicture) {
        tutorService.createTutor(request, profilePicture);
        return new ResponseEntity<>("Register", HttpStatus.CREATED);
    }

    @PostMapping("/register/student")
    public ResponseEntity<String> register(@RequestBody StudentRequest request ,
                                           @RequestParam(required = false) MultipartFile profilePicture) {
        studentService.createStudent(request, profilePicture);
        return new ResponseEntity<>("Register", HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        emailService.verifyToken(token);
        return new ResponseEntity<>("Verified successfully", HttpStatus.OK);
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<Map <String, Object>> user(@RequestParam String token ) {
        return new ResponseEntity<>(userService.sideAuth(token),HttpStatus.OK);
    }


}
