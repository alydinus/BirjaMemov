package kg.alatoo.labor_exchange.controller;

import kg.alatoo.labor_exchange.payload.request.StudentRequest;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import kg.alatoo.labor_exchange.service.StudentService;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final TutorService tutorService;
    private final StudentService studentService;

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

}
