package activemqinaction.ch7.sync;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;
import java.util.UUID;

public class Client implements MessageListener {

	private String brokerUrl = "tcp://0.0.0.0:61616";
	private String requestQueue = "requests";
	
	Connection connection;
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	private Destination tempDest;
	
	public void start() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				brokerUrl);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination adminQueue = session.createQueue(requestQueue);

		producer = session.createProducer(adminQueue);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		tempDest = session.createTemporaryQueue();
		consumer = session.createConsumer(tempDest);

		consumer.setMessageListener(this);
	}

	public void stop() throws JMSException {
		producer.close();
		consumer.close();
		session.close();
		connection.close();
	}
	
	public void request(String request) throws JMSException {
		System.out.println("Requesting: " + request);
		TextMessage txtMessage = session.createTextMessage();
		txtMessage.setText(request);

		txtMessage.setJMSReplyTo(tempDest);

		String correlationId = UUID.randomUUID().toString();
		txtMessage.setJMSCorrelationID(correlationId);
		this.producer.send(txtMessage);
	}

	public void onMessage(Message message) {
		try {
			System.out.println("Received response for: "
					+ ((TextMessage) message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Client client = new Client();
		client.start();
		int i = 0;
		while (i++ < 10) {
			client.request("REQUEST-" + i);
		}
		Thread.sleep(3000); //wait for replies
		client.stop();
	}

}
