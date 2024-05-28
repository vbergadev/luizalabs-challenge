package br.com.luizalabs_challenge.luizalabs.rabbitmq;

import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueSender {
    @Value("${rabbitmq.topic}")
    private String topic;

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @SneakyThrows
    public void send(final ScheduleCommunication scheduleComunication) {
        final var resultJson = objectMapper.writeValueAsString(scheduleComunication);
        log.info("Sending message to RabbitMQ: {}", scheduleComunication);
        rabbitTemplate.convertAndSend(topic, "", resultJson);
    }
}
