# Desafio Luizalabs

Este é um aplicativo Spring Boot desenvolvido para o Desafio Luizalabs. O aplicativo fornece uma API RESTful para agendamento de comunicações.

## Tecnologias Utilizadas

- Java
- Spring Boot
- SQL
- Maven

## Modelos

O aplicativo possui os seguintes modelos:

- `ScheduleCommunication`: Representa uma comunicação agendada. Possui campos para o horário agendado, destinatário, mensagem, status e tipo de comunicação (email, SMS, push, WhatsApp).

- `BaseEntity`: Uma classe base para entidades. Fornece campos para UUID, versão, criado em, atualizado em e status ativo.

- `CommunicationStatus`: Um enum representando o status de uma comunicação. Pode ser PENDING (pendente), SENT (enviado) ou CANCELED (cancelado).

- `SendMethod`: Um enum representando o método de envio de uma comunicação. Pode ser EMAIL, SMS, PUSH ou WHATSAPP.

## Endpoints

O aplicativo fornece os seguintes endpoints:

- GET `/schedule-communication/`: Listar todos os `ScheduleCommunications` ativos.
- GET `/schedule-communication/recipient/{recipient}`: Obter `ScheduleCommunications` por destinatário.
- POST `/schedule-communication/`: Adicionar novos `ScheduleCommunication`.

Body Example:
```json
[
  {
    "message": "Message 01",
    "recipient": "sample@mail.com",
    "scheduled_time": "2024-06-02 10:10:00",
    "status": "PENDING",
    "type": "EMAIL"
  },
  {
    "message": "Test message02",
    "recipient": "11999999999",
    "scheduled_time": "2024-06-02 11:00:00",
    "status": "CANCELED",
    "type": "SMS"
  },
  {
    "message": "Test message03",
    "recipient": "Carlosd Jr2",
    "scheduled_time": "2024-05-02 12:00:00",
    "status": "SENT",
    "type": "WHATSAPP"
  }
]
```
- PATCH `/schedule-communication/{id}/type`: Atualizar o tipo de um `ScheduleCommunication`.
  Body Example:
```json
"SMS"
```
- PATCH `/schedule-communication/{id}/status`: Atualizar o status de um `ScheduleCommunication`.
  Body Example:
```json
"CANCELED"
```

- PATCH `/schedule-communication/{id}/recipient`: Atualizar o destinatário de um `ScheduleCommunication`.
  Body Example:
```json
example@mail.com
```
## Executando o Aplicativo em modo Dev

Para executar o aplicativo em modo dev, use os seguintes comandos:

Vá para a paste dev
```bash
cd dev
```
Suba o docker
```bash
docker-compose up --build
```
Execute o aplicativo

clique no botão "PLAY" no arquivo `LuizalabsApplication.java` ou no canto superior direito do IntelliJ IDEA.

## Executando o Aplicativo  completo, use o seguinte comando:

Suba o docker
```bash
docker-compose up --build
```