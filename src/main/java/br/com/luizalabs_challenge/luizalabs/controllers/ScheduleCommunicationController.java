package br.com.luizalabs_challenge.luizalabs.controllers;

import br.com.luizalabs_challenge.luizalabs.controllers.dto.ScheduleCommunicationDto;
import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.rabbitmq.QueueSender;
import br.com.luizalabs_challenge.luizalabs.repositories.ScheduleCommunicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public List<ScheduleCommunication> listOnlyActives() {
        return repository.findAll();
    }
}
