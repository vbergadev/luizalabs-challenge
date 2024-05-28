package br.com.luizalabs_challenge.luizalabs.rabbitmq;

import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QueueSenderTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private QueueSender queueSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSend() throws Exception {
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        scheduleCommunication.setRecipient("test@example.com");

        String expectedJson = "{\"recipient\":\"test@example.com\"}";
        when(objectMapper.writeValueAsString(scheduleCommunication)).thenReturn(expectedJson);

        java.lang.reflect.Field topicField = QueueSender.class.getDeclaredField("topic");
        topicField.setAccessible(true);
        topicField.set(queueSender, "test-topic");

        queueSender.send(scheduleCommunication);

        ArgumentCaptor<String> jsonCaptor = ArgumentCaptor.forClass(String.class);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("test-topic"), eq(""), jsonCaptor.capture());

        String actualJson = jsonCaptor.getValue();
        assertEquals(expectedJson, actualJson);
    }
}
