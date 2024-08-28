package activemqinaction.ch14.advisory;

import org.apache.activemq.command.*;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;

public class ConsumerAdvisoryListener implements MessageListener {

	public void onMessage(Message message) {
		ActiveMQMessage msg = (ActiveMQMessage) message;
		DataStructure ds = msg.getDataStructure();
		if (ds != null) {
			switch (ds.getDataStructureType()) {
			case CommandTypes.CONSUMER_INFO:
				ConsumerInfo consumerInfo = (ConsumerInfo) ds;
				System.out.println("Consumer '" + consumerInfo.getConsumerId()
						+ "' subscribed to '" + consumerInfo.getDestination()
						+ "'");
				break;
			case CommandTypes.REMOVE_INFO:
				RemoveInfo removeInfo = (RemoveInfo) ds;
				ConsumerId consumerId = ((ConsumerId) removeInfo.getObjectId());
				System.out
						.println("Consumer '" + consumerId + "' unsubscribed");
				break;
			default:
				System.out.println("Unkown data structure type");
			}
		} else {
			System.out.println("No data structure provided");
		}
	}
}
