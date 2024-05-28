package br.com.luizalabs_challenge.luizalabs.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleCommunicationTest {

    @Test
    public void testScheduleCommunicationBuilderAndGetters() {
        // Arrange
        LocalDateTime scheduledTime = LocalDateTime.of(2024, 5, 27, 10, 0);
        String recipient = "recipient@example.com";
        String message = "Test message";
        CommunicationStatus status = CommunicationStatus.SENT;
        SendMethod type = SendMethod.EMAIL;

        // Act
        ScheduleCommunication scheduleCommunication = ScheduleCommunication.builder()
                .scheduledTime(scheduledTime)
                .recipient(recipient)
                .message(message)
                .status(status)
                .type(type)
                .build();

        // Assert
        assertThat(scheduleCommunication).isNotNull();
        assertThat(scheduleCommunication.getScheduledTime()).isEqualTo(scheduledTime);
        assertThat(scheduleCommunication.getRecipient()).isEqualTo(recipient);
        assertThat(scheduleCommunication.getMessage()).isEqualTo(message);
        assertThat(scheduleCommunication.getStatus()).isEqualTo(status);
        assertThat(scheduleCommunication.getType()).isEqualTo(type);
    }

    @Test
    public void testScheduleCommunicationSetters() {
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();

        LocalDateTime scheduledTime = LocalDateTime.of(2024, 5, 27, 10, 0);
        String recipient = "recipient@example.com";
        String message = "Test message";
        CommunicationStatus status = CommunicationStatus.SENT;
        SendMethod type = SendMethod.EMAIL;

        scheduleCommunication.setScheduledTime(scheduledTime);
        scheduleCommunication.setRecipient(recipient);
        scheduleCommunication.setMessage(message);
        scheduleCommunication.setStatus(status);
        scheduleCommunication.setType(type);

        assertThat(scheduleCommunication.getScheduledTime()).isEqualTo(scheduledTime);
        assertThat(scheduleCommunication.getRecipient()).isEqualTo(recipient);
        assertThat(scheduleCommunication.getMessage()).isEqualTo(message);
        assertThat(scheduleCommunication.getStatus()).isEqualTo(status);
        assertThat(scheduleCommunication.getType()).isEqualTo(type);
    }

    @Test
    public void testScheduleCommunicationEqualsAndHashCode() {
        ScheduleCommunication scheduleCommunication1 = ScheduleCommunication.builder()
                .scheduledTime(LocalDateTime.of(2024, 5, 27, 10, 0))
                .recipient("recipient@example.com")
                .message("Test message")
                .status(CommunicationStatus.SENT)
                .type(SendMethod.EMAIL)
                .build();

        ScheduleCommunication scheduleCommunication2 = ScheduleCommunication.builder()
                .scheduledTime(LocalDateTime.of(2024, 5, 27, 10, 0))
                .recipient("recipient@example.com")
                .message("Test message")
                .status(CommunicationStatus.SENT)
                .type(SendMethod.EMAIL)
                .build();

        assertThat(scheduleCommunication1).isEqualTo(scheduleCommunication2);
        assertThat(scheduleCommunication1.hashCode()).isEqualTo(scheduleCommunication2.hashCode());
    }
}