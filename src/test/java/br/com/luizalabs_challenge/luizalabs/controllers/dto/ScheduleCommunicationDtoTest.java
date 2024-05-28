package br.com.luizalabs_challenge.luizalabs.controllers.dto;

import br.com.luizalabs_challenge.luizalabs.models.CommunicationStatus;
import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.models.SendMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleCommunicationDtoTest {

    @Test
    public void testToScheduleCommunication() {
        String message = "Test message";
        String recipient = "test@example.com";
        LocalDateTime scheduledTime = LocalDateTime.of(2023, 5, 28, 14, 30, 0);
        CommunicationStatus status = CommunicationStatus.PENDING;
        SendMethod type = SendMethod.EMAIL;

        ScheduleCommunicationDto dto = ScheduleCommunicationDto.builder()
                .message(message)
                .recipient(recipient)
                .scheduledTime(scheduledTime)
                .status(status)
                .type(type)
                .build();

        ScheduleCommunication scheduleCommunication = dto.toScheduleCommunication();

        assertNotNull(scheduleCommunication);
        assertEquals(message, scheduleCommunication.getMessage());
        assertEquals(recipient, scheduleCommunication.getRecipient());
        assertEquals(scheduledTime, scheduleCommunication.getScheduledTime());
        assertEquals(status, scheduleCommunication.getStatus());
        assertEquals(type, scheduleCommunication.getType());
    }

    @Test
    public void testAllArgsConstructor() {
        String message = "Test message";
        String recipient = "test@example.com";
        LocalDateTime scheduledTime = LocalDateTime.of(2023, 5, 28, 14, 30, 0);
        CommunicationStatus status = CommunicationStatus.PENDING;
        SendMethod type = SendMethod.EMAIL;

        ScheduleCommunicationDto dto = new ScheduleCommunicationDto(
                message, recipient, scheduledTime, status, type);

        assertEquals(message, dto.getMessage());
        assertEquals(recipient, dto.getRecipient());
        assertEquals(scheduledTime, dto.getScheduledTime());
        assertEquals(status, dto.getStatus());
        assertEquals(type, dto.getType());
    }

    @Test
    public void testNoArgsConstructor() {
        ScheduleCommunicationDto dto = new ScheduleCommunicationDto();

        assertNull(dto.getMessage());
        assertNull(dto.getRecipient());
        assertNull(dto.getScheduledTime());
        assertNull(dto.getStatus());
        assertNull(dto.getType());
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{\"message\":\"Test message\",\"recipient\":\"test@example.com\",\"scheduled_time\":\"2023-05-28 14:30:00\",\"status\":\"PENDING\",\"type\":\"EMAIL\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ScheduleCommunicationDto dto = objectMapper.readValue(json, ScheduleCommunicationDto.class);

        assertEquals("Test message", dto.getMessage());
        assertEquals("test@example.com", dto.getRecipient());
        assertEquals(LocalDateTime.of(2023, 5, 28, 14, 30, 0), dto.getScheduledTime());
        assertEquals(CommunicationStatus.PENDING, dto.getStatus());
        assertEquals(SendMethod.EMAIL, dto.getType());
    }

}
