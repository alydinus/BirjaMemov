package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Authority;
import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.entity.User;
import kg.alatoo.labor_exchange.repository.StudentRepository;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.repository.UserRepository;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import kg.alatoo.labor_exchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final  JwtUtil jwtUtil;

    public UserServiceImpl(TutorRepository tutorRepository, StudentRepository studentRepository, UserRepository userRepository,@Lazy JwtUtil jwtUtil) {
        this.tutorRepository = tutorRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public void updateRefresh(Map<String, String> map) {
        Optional<Tutor> tutorOpt = tutorRepository.findByUsername(map.get("username"));
        Optional<Student> studentOpt = studentRepository.findByUsername(map.get("username"));
        if(tutorOpt.isPresent()) {
            Tutor tutor = tutorOpt.get();
            tutor.setRefreshToken(map.get("refresh_token"));
            tutorRepository.save(tutor);
        } else if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setRefreshToken(map.get("refresh_token"));
            studentRepository.save(student);
        } else{
            throw new RuntimeException("Refresh token was damaged");
        }


    }

    public User getUserByUsername(String username) {
        return (User) userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User getUserByEmail(String email) {
        return (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public Map<String, Object> sideAuth(String email) {
        User u = getUserByEmail(email);
        Set<Authority> roles = u.getAuthorities();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Authority role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        String accessToken = jwtUtil.generateToken(authorities,u.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(authorities,u.getUsername());
        return Map.of("access_token",accessToken,
                "refresh_token",refreshToken,
                "username",u.getUsername());
    }
}
