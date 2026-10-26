package com.example;

import java.util.Scanner;

import jakarta.jms.Connection;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public final class Publisher {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "NewsTopic";

    private Publisher() {
    }

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);

        try (Connection connection = factory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(session.createTopic(TOPIC_NAME));
                Scanner scanner = new Scanner(System.in)) {
            connection.start();

            while (true) {
                System.out.print("Enter message (type 'exit' to stop): ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String text = scanner.nextLine();
                if ("exit".equalsIgnoreCase(text.trim())) {
                    break;
                }

                producer.send(session.createTextMessage(text));
                System.out.println("Published: " + text);
                System.out.println();
            }
        }
    }
}
