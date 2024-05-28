package br.com.luizalabs_challenge.luizalabs.rabbitmq;

import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.repositories.ScheduleCommunicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class QueueListenerTest {

    @Mock
    private ScheduleCommunicationRepository scheduleCommunicationRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private QueueListener queueListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceive() throws Exception {
        String scheduleJson = "{\"recipient\":\"test@example.com\"}";
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        scheduleCommunication.setRecipient("test@example.com");

        Channel channel = mock(Channel.class);
        long tag = 1L;

        when(objectMapper.readValue(scheduleJson, ScheduleCommunication.class)).thenReturn(scheduleCommunication);

        queueListener.receive(scheduleJson, channel, tag);

        ArgumentCaptor<ScheduleCommunication> captor = ArgumentCaptor.forClass(ScheduleCommunication.class);
        verify(scheduleCommunicationRepository, times(1)).save(captor.capture());

        ScheduleCommunication savedScheduleCommunication = captor.getValue();
        assertEquals("test@example.com", savedScheduleCommunication.getRecipient());
    }

    @Test
    public void testReceiveThrowsException() throws Exception {
        String scheduleJson = "{\"recipient\":\"test@example.com\"}";
        Channel channel = mock(Channel.class);
        long tag = 1L;

        when(objectMapper.readValue(scheduleJson, ScheduleCommunication.class)).thenThrow(new RuntimeException("Deserialization error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            queueListener.receive(scheduleJson, channel, tag);
        });

        assertEquals("Deserialization error", exception.getMessage());
        verify(scheduleCommunicationRepository, never()).save(any(ScheduleCommunication.class));
    }
}
