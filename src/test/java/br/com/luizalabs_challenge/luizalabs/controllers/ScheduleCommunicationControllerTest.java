package br.com.luizalabs_challenge.luizalabs.controllers;

import br.com.luizalabs_challenge.luizalabs.controllers.dto.ScheduleCommunicationDto;
import br.com.luizalabs_challenge.luizalabs.models.CommunicationStatus;
import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.models.SendMethod;
import br.com.luizalabs_challenge.luizalabs.rabbitmq.QueueSender;
import br.com.luizalabs_challenge.luizalabs.repositories.ScheduleCommunicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleCommunicationControllerTest {

    @Mock
    private QueueSender queueSender;

    @Mock
    private ScheduleCommunicationRepository repository;

    @InjectMocks
    private ScheduleCommunicationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ScheduleCommunicationController(queueSender, repository);
    }


    @Test
    public void testAdd() {
        ScheduleCommunicationDto dto = new ScheduleCommunicationDto();
        List<ScheduleCommunicationDto> dtoList = List.of(dto);

        controller.add(dtoList);

        verify(queueSender, times(1)).send(any(ScheduleCommunication.class));
    }

    @Test
    public void testCancel() {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        controller.cancel(id);

        assertEquals(false, scheduleCommunication.isActive());
        verify(queueSender, times(1)).send(scheduleCommunication);
    }

    @Test
    public void testPatchStatus() {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        CommunicationStatus status = CommunicationStatus.SENT;

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        controller.patchStatus(id, status);

        assertEquals(status, scheduleCommunication.getStatus());
        verify(queueSender, times(1)).send(scheduleCommunication);
    }

    @Test
    public void testPatchStatusFailure() {
        UUID id = UUID.randomUUID();
        CommunicationStatus status = CommunicationStatus.SENT;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> controller.patchStatus(id, status));
    }

    @Test
    public void testPatchRecipient() {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        String recipient = "test@example.com";

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        controller.patchRecipient(id, recipient);

        assertEquals(recipient, scheduleCommunication.getRecipient());
        verify(queueSender, times(1)).send(scheduleCommunication);
    }

    @Test
    public void testPatchRecipientFailure() {
        UUID id = UUID.randomUUID();
        String recipient = "test@example.com";

        when(repository.findById(id)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> controller.patchRecipient(id, recipient));
    }

    @Test
    public void testPatchType() {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        SendMethod type = SendMethod.EMAIL;

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        controller.patchType(id, type);

        assertEquals(type, scheduleCommunication.getType());
        verify(queueSender, times(1)).send(scheduleCommunication);
    }

    @Test
    public void testPatchTypeFailure() {
        UUID id = UUID.randomUUID();
        SendMethod type = SendMethod.EMAIL;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> controller.patchType(id, type));
    }

    @Test
    public void testListOnlyActives() {
        ScheduleCommunication activeCommunication = new ScheduleCommunication();
        activeCommunication.setActive(true);

        when(repository.findAll()).thenReturn(List.of(activeCommunication));

        List<ScheduleCommunication> result = controller.listOnlyActives();

        assertEquals(1, result.size());
        assertEquals(activeCommunication, result.get(0));
    }

    @Test
    public void testGetByRecipient() {
        String recipient = "test@example.com";
        ScheduleCommunication communication = new ScheduleCommunication();
        communication.setRecipient(recipient);

        when(repository.findByRecipient(recipient)).thenReturn(List.of(communication));

        List<ScheduleCommunication> result = controller.getByRecipient(recipient);

        assertEquals(1, result.size());
        assertEquals(communication, result.get(0));
    }

    @Test
    public void testGetById() {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        ScheduleCommunication result = controller.getById(id);

        assertEquals(scheduleCommunication, result);
    }
}
