package kg.alatoo.labor_exchange.exception.exceptions;

public class EmailTokenExpiredException extends RuntimeException {
  public EmailTokenExpiredException(String message) {
    super(message);
  }
}
