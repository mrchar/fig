package net.mrchar.fig.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
// @Component
public class ChargeCreditCardWorker {
  @JobWorker(type = "charge-credit-card")
  public Map<String, Double> chargeCreditCard(
      @Variable(name = "totalWithTax") Double totalWithTax) {
    log.info("charge credit card:{}", totalWithTax);
    return Map.of("amountCharged", totalWithTax);
  }
}
