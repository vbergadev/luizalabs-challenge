package br.com.luizalabs_challenge.luizalabs.controllers;

import br.com.luizalabs_challenge.luizalabs.controllers.dto.ScheduleCommunicationDto;
import br.com.luizalabs_challenge.luizalabs.models.CommunicationStatus;
import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.models.SendMethod;
import br.com.luizalabs_challenge.luizalabs.rabbitmq.QueueSender;
import br.com.luizalabs_challenge.luizalabs.repositories.ScheduleCommunicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule-communication")
@RequiredArgsConstructor
public class ScheduleCommunicationController {
    final QueueSender queueSender;
    final ScheduleCommunicationRepository repository;

    @PostMapping("/")
    public void add(@RequestBody List<ScheduleCommunicationDto> dto) {
        for (final var scheduleCommunicationDto : dto) {
            queueSender.send(scheduleCommunicationDto.toScheduleCommunication());
        }
    }

    @PatchMapping("/{id}")
    public void cancel(@PathVariable UUID id) {
        final var scheduleCommunication = repository.findById(id).orElseThrow();
        scheduleCommunication.setActive(false);
        queueSender.send(scheduleCommunication);
    }

    @PatchMapping("/{id}/status")
    public void patchStatus(@PathVariable UUID id, @RequestBody CommunicationStatus status) {
        ScheduleCommunication scheduleCommunication = repository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
        scheduleCommunication.setStatus(status);
        queueSender.send(scheduleCommunication);
    }

    @PatchMapping("/{id}/recipient")
    public void patchRecipient(@PathVariable UUID id, @RequestBody String recipient) {
        ScheduleCommunication scheduleCommunication = repository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
        scheduleCommunication.setRecipient(recipient);
        queueSender.send(scheduleCommunication);
    }
    @PatchMapping("/{id}/type")
    public void patchType(@PathVariable UUID id, @RequestBody SendMethod type) {
        ScheduleCommunication scheduleCommunication = repository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
        scheduleCommunication.setType(type);
        queueSender.send(scheduleCommunication);
    }

    @GetMapping("/")
    public List<ScheduleCommunication> listOnlyActives() {
        return repository.findAll();
    }

    @GetMapping("/recipient/{recipient}")
    public List<ScheduleCommunication> getByRecipient(@PathVariable String recipient) {
        return repository.findByRecipient(recipient);
    }

    @GetMapping("/{id}")
    public ScheduleCommunication getById(@PathVariable UUID id) {
        return repository.findById(id).orElseThrow();
    }

}
