package activemqinaction.ch14.advisory;

import org.apache.activemq.command.ActiveMQMapMessage;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;

public class NoConsumerAdvisoryListener implements MessageListener {

	public void onMessage(Message message) {
		try {
			System.out.println("Message " + ((ActiveMQMapMessage)message).getContentMap() + " not consumed by any consumer");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
