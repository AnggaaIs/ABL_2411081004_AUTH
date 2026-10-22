package com.angga.auth.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angga.auth.config.RabbitMQConfig;
import com.angga.auth.dto.UserRegisteredEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class UserEventPublisher {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public void publishUserRegistered(UserRegisteredEvent event) {
    try {
      String payload = objectMapper.writeValueAsString(event);
      rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTERED_QUEUE, payload);
      System.out.println("Publish: " + event.getEmail());
    } catch (Exception e) {
      System.err.println("Gagal publish user registered event: " + e.getMessage());
    }
  }
}