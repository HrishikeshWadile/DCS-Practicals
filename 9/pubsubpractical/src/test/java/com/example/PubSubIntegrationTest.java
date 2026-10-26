package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import jakarta.jms.Connection;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.junit.Test;

public class PubSubIntegrationTest {
    @Test
    public void publishesTextToAllConnectedTopicSubscribers() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("pubsub-test-broker");
        broker.setPersistent(false);
        broker.setUseJmx(false);
        TransportConnector connector = broker.addConnector("tcp://127.0.0.1:0");

        try {
            broker.start();
            broker.waitUntilStarted();

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                    connector.getPublishableConnectString());
            try (Connection connection = factory.createConnection();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                Topic topic = session.createTopic("NewsTopic");
                try (MessageConsumer firstSubscriber = session.createConsumer(topic);
                        MessageConsumer secondSubscriber = session.createConsumer(topic);
                        MessageProducer publisher = session.createProducer(topic)) {
                    connection.start();
                    publisher.send(session.createTextMessage("Hello Students"));

                    assertTextMessage(firstSubscriber.receive(5000), "Hello Students");
                    assertTextMessage(secondSubscriber.receive(5000), "Hello Students");
                }
            }
        } finally {
            broker.stop();
            broker.waitUntilStopped();
        }
    }

    private static void assertTextMessage(Message message, String expectedText) throws Exception {
        assertTrue("Expected a text message", message instanceof TextMessage);
        assertEquals(expectedText, ((TextMessage) message).getText());
    }
}
