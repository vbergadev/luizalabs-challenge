FROM rabbitmq:management

COPY /rabbitmq.conf /etc/rabbitmq/rabbitmq.conf
COPY /definitions.json /etc/rabbitmq/definitions.json

EXPOSE 5672
EXPOSE 15672

CMD ["rabbitmq-server"]
