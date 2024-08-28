package activemqinaction.ch2.portfolio;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;

import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Map;


public class Publisher {

    private static final Logger log = LoggerFactory.getLogger(Publisher.class);

    protected int MAX_DELTA_PERCENT = 1;
    protected Map<String, Double> LAST_PRICES = new Hashtable<>();
    protected static int count = 10;
    protected static int total;

    protected static String brokerURL = "tcp://localhost:61616";
    protected static ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    protected transient MessageProducer producer;

    public Publisher() throws JMSException {
        factory = new ActiveMQConnectionFactory(brokerURL);
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        Publisher publisher = new Publisher();
        while (total < 1000) {
            log.info("Running total... {}", total);
            for (int i = 0; i < count; i++) {
                publisher.sendMessage(args);
            }
            total += count;
            log.info("Published {} of  {} price messages", count, total);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException x) {
                log.error(x.getLocalizedMessage());
            }
        }
        publisher.close();
    }

    protected void sendMessage(String[] stocks) throws JMSException {
        int idx;
        while (true) {
            idx = (int) Math.round(stocks.length * Math.random());
            if (idx < stocks.length) {
                break;
            }
        }
        String stock = stocks[idx];
        Destination destination = session.createTopic("STOCKS." + stock);
        Message message = createStockMessage(stock, session);
        log.info("Sending: {} on destination: {}", ((ActiveMQMapMessage) message).getContentMap(), destination);
        producer.send(destination, message);
    }

    protected Message createStockMessage(String stock, Session session) throws JMSException {
        Double value = LAST_PRICES.get(stock);
        if (value == null) {
            value = Math.random() * 100;
        }

        // lets mutate the value by some percentage
        double oldPrice = value;
        value = mutatePrice(oldPrice);
        LAST_PRICES.put(stock, value);
        double price = value;

        double offer = price * 1.001;

        boolean up = (price > oldPrice);

        MapMessage message = session.createMapMessage();
        message.setString("stock", stock);
        message.setDouble("price", price);
        message.setDouble("offer", offer);
        message.setBoolean("up", up);
        return message;
    }

    protected double mutatePrice(double price) {
        double percentChange = (2 * Math.random() * MAX_DELTA_PERCENT) - MAX_DELTA_PERCENT;

        return price * (100 + percentChange) / 100;
    }

}
