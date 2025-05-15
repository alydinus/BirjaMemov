package kg.alatoo.labor_exchange.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageProperties {

  @Value("${STORAGE_LOCATION}")
  private String location;

}
