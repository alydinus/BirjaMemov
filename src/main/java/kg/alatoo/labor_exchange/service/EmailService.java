package kg.alatoo.labor_exchange.service;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String content);
    Boolean verifyToken(String token);
}
