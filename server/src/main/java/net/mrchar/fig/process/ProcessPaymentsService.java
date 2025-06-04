package net.mrchar.fig.process;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
// @Service
@RequiredArgsConstructor
public class ProcessPaymentsService {
  private final ZeebeClient zeebeClient;

  @PostConstruct
  public void init() {
    String bpmnProcessId = "process-payments";
    ProcessInstanceEvent event =
        zeebeClient
            .newCreateInstanceCommand()
            .bpmnProcessId(bpmnProcessId)
            .latestVersion()
            .variables((Map.of("total", 100)))
            .send()
            .join();

    log.info("started a process instance: {}", event.getProcessInstanceKey());
  }
}
