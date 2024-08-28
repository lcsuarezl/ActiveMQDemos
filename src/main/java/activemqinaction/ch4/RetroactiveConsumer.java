package activemqinaction.ch4;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;


public class RetroactiveConsumer {
    
    public void createRetroactiveConsumer() throws JMSException  {
        
        ConnectionFactory fac = new ActiveMQConnectionFactory();        
        Connection connection = fac.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("TEST.TOPIC?consumer.retroactive=true");
        MessageConsumer consumer = session.createConsumer(topic);
    }
}
