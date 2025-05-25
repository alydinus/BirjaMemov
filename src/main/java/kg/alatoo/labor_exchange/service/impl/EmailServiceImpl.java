package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.entity.User;
import kg.alatoo.labor_exchange.exception.exceptions.EmailTokenExpiredException;
import kg.alatoo.labor_exchange.repository.UserRepository;
import kg.alatoo.labor_exchange.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final UserRepository userRepository;


    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            emailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending email", e);
        }

    }

    @Override
    public Boolean verifyToken(String token) {
        Optional<User> userOptional = userRepository.findByEmailToken((token));
        if (!userOptional.isPresent()) {
            return false;
        }

        User user = userOptional.get();
        if(!user.getVerificationCodeExpiration().after(Timestamp.valueOf(LocalDateTime.now()))){

            user.setVerificationCode(UUID.randomUUID().toString());
            user.setVerificationCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
            userRepository.save(user);

            sendSimpleMail(user.getEmail(), "Verification",
                    "Verification code: " + user.getVerificationCode().toString() +
                            "\n Link/Postman request: POST http://localhost:4445/api/auth/verify?token=" + user.getVerificationCode());
            throw new EmailTokenExpiredException("Email token expired");

        } else{
            user.setIsEmailVerified(true);
            user.setVerificationCodeExpiration(null);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }


    }

}
