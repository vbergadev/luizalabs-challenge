package br.com.luizalabs_challenge.luizalabs.models;

import br.com.luizalabs_challenge.luizalabs.controllers.ScheduleCommunicationController;
import br.com.luizalabs_challenge.luizalabs.controllers.dto.ScheduleCommunicationDto;
import br.com.luizalabs_challenge.luizalabs.rabbitmq.QueueSender;
import br.com.luizalabs_challenge.luizalabs.repositories.ScheduleCommunicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ScheduleCommunicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QueueSender queueSender;

    @Mock
    private ScheduleCommunicationRepository repository;

    @InjectMocks
    private ScheduleCommunicationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private void setId(ScheduleCommunication scheduleCommunication, UUID id) {
        try {
            Field idField = ScheduleCommunication.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(scheduleCommunication, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAdd() throws Exception {
        ScheduleCommunicationDto dto = new ScheduleCommunicationDto();
        List<ScheduleCommunicationDto> dtoList = List.of(dto);

        mockMvc.perform(post("/schedule-communication/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{}]"))
                .andExpect(status().isOk());

        verify(queueSender, times(1)).send(any(ScheduleCommunication.class));
    }

    @Test
    public void testCancel() throws Exception {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        setId(scheduleCommunication, id);

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        mockMvc.perform(patch("/schedule-communication/" + id))
                .andExpect(status().isOk());

        assertEquals(false, scheduleCommunication.isActive());
        verify(queueSender, times(1)).send(any(ScheduleCommunication.class));
    }

    @Test
    public void testPatchStatus() throws Exception {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        setId(scheduleCommunication, id);
        CommunicationStatus status = CommunicationStatus.SENT;

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        mockMvc.perform(patch("/schedule-communication/" + id + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"SENT\"}"))
                .andExpect(status().isOk());

        assertEquals(status, scheduleCommunication.getStatus());
        verify(queueSender, times(1)).send(any(ScheduleCommunication.class));
    }

    @Test
    public void testPatchRecipient() throws Exception {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        setId(scheduleCommunication, id);
        String recipient = "test@example.com";

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        mockMvc.perform(patch("/schedule-communication/" + id + "/recipient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + recipient + "\""))
                .andExpect(status().isOk());

        assertEquals(recipient, scheduleCommunication.getRecipient());
        verify(queueSender, times(1)).send(any(ScheduleCommunication.class));
    }

    @Test
    public void testPatchType() throws Exception {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        setId(scheduleCommunication, id);
        SendMethod type = SendMethod.EMAIL;

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        mockMvc.perform(patch("/schedule-communication/" + id + "/type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"EMAIL\""))
                .andExpect(status().isOk());

        assertEquals(type, scheduleCommunication.getType());
        verify(queueSender, times(1)).send(any(ScheduleCommunication.class));
    }

    @Test
    public void testListOnlyActives() throws Exception {
        ScheduleCommunication activeCommunication = new ScheduleCommunication();
        activeCommunication.setActive(true);

        when(repository.findAll()).thenReturn(List.of(activeCommunication));

        mockMvc.perform(get("/schedule-communication/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetByRecipient() throws Exception {
        String recipient = "test@example.com";
        ScheduleCommunication communication = new ScheduleCommunication();
        communication.setRecipient(recipient);

        when(repository.findByRecipient(recipient)).thenReturn(List.of(communication));

        mockMvc.perform(get("/schedule-communication/recipient/" + recipient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetById() throws Exception {
        UUID id = UUID.randomUUID();
        ScheduleCommunication scheduleCommunication = new ScheduleCommunication();
        setId(scheduleCommunication, id);

        when(repository.findById(id)).thenReturn(Optional.of(scheduleCommunication));

        mockMvc.perform(get("/schedule-communication/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }
}
