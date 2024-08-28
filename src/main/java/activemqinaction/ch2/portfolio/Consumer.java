package activemqinaction.ch2.portfolio;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private static final String brokerURL = "tcp://localhost:61616";
    private static ConnectionFactory factory;
    private final transient Connection connection;
    private final transient Session session;
    
    public Consumer() throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
    public static void main(String[] args) throws JMSException {
    	Consumer consumer = new Consumer();
    	for (String stock : args) {
            log.info("Listening stock: {}", stock);
    		Destination destination = consumer.getSession().createTopic("STOCKS." + stock);
            MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
            messageConsumer.setMessageListener(new Listener());
        }
    }
	
	public Session getSession() {
		return session;
	}

}
