package br.com.luizalabs_challenge.luizalabs.controllers.dto;

import br.com.luizalabs_challenge.luizalabs.models.CommunicationStatus;
import br.com.luizalabs_challenge.luizalabs.models.ScheduleCommunication;
import br.com.luizalabs_challenge.luizalabs.models.SendMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleCommunicationDto {
    private String message;
    private String recipient;
    @JsonProperty("scheduled_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledTime;
    private CommunicationStatus status;
    private SendMethod type;

    public ScheduleCommunication toScheduleCommunication() {
        return ScheduleCommunication.builder().message(message).recipient(recipient).scheduledTime(scheduledTime).status(status).type(type).build();
    }

}
