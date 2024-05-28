package br.com.luizalabs_challenge.luizalabs.repositories;

import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleCommunicationRepository extends JpaRepository<ScheduleCommunication, UUID>{
    List<ScheduleCommunication> findByActive(boolean isActive);
    List<ScheduleCommunication> findByRecipient(String recipient);
}
