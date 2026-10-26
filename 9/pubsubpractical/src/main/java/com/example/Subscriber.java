package com.example;

import jakarta.jms.Connection;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public final class Subscriber {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "NewsTopic";

    private Subscriber() {
    }

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);

        try (Connection connection = factory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageConsumer consumer = session.createConsumer(session.createTopic(TOPIC_NAME))) {
            connection.start();

            System.out.println("================================");
            System.out.println("       MESSAGE SUBSCRIBER");
            System.out.println("================================");
            System.out.println("Subscribed to " + TOPIC_NAME + ".");
            System.out.println("Waiting for messages...");

            while (true) {
                Message message = consumer.receive();
                if (message instanceof TextMessage textMessage) {
                    System.out.println();
                    System.out.println("Received: " + textMessage.getText());
                }
            }
        }
    }
}
