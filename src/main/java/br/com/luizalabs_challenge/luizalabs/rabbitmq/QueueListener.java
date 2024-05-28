package br.com.luizalabs_challenge.luizalabs.rabbitmq;

import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.repositories.ScheduleCommunicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    private final ScheduleCommunicationRepository scheduleCommunicationRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue}")
    @SneakyThrows
    public void receive(
            @Payload String scheduleJson, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            final var schedule = objectMapper.readValue(scheduleJson, ScheduleCommunication.class);
            log.info("Salvando: {}", schedule);
            scheduleCommunicationRepository.save(schedule);
        } catch (Exception ex) {
            throw ex;
        }
    }
}

