package activemqinaction.ch6;

import activemqinaction.ch2.portfolio.Listener;
import org.apache.activemq.ActiveMQConnectionFactory;


import jakarta.jms.*;

public class Consumer {

    private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    
    private String username = "guest";
    private String password = "password";
    
    public Consumer() throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection(username, password);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
    public static void main(String[] args) throws Exception {
    	Consumer consumer = new Consumer();
    	try {
	    	for (String stock : args) {
	    		Destination destination = consumer.getSession().createTopic("STOCKS." + stock);
	    		MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
	    		messageConsumer.setMessageListener(new Listener());
	    	}
	    	System.out.println("Press any key to exit");
	    	System.in.read();
    	} finally {
    		consumer.close();
    	}
    }
	
	public Session getSession() {
		return session;
	}

}
