package com.example;

import java.util.concurrent.CountDownLatch;

import org.apache.activemq.broker.BrokerService;

public final class MessageBroker {
    private static final String BROKER_URL = "tcp://localhost:61616";

    private MessageBroker() {
    }

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("localhost");
        broker.setPersistent(false);
        broker.setUseJmx(false);
        broker.addConnector(BROKER_URL);
        broker.start();
        broker.waitUntilStarted();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                broker.stop();
                broker.waitUntilStopped();
            } catch (Exception e) {
                System.err.println("Error stopping broker: " + e.getMessage());
            }
        }));

        System.out.println("================================");
        System.out.println("   MESSAGE BROKER STARTED");
        System.out.println("================================");
        System.out.println("Broker URL: " + BROKER_URL);
        System.out.println("Waiting for publishers and subscribers...");

        new CountDownLatch(1).await();
    }
}
