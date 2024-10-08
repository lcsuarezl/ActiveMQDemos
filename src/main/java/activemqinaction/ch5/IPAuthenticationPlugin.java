package activemqinaction.ch5;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;

import java.util.List;

public class IPAuthenticationPlugin implements BrokerPlugin {
	
	List<String> allowedIPAddresses;

	public Broker installPlugin(Broker broker) throws Exception {
		return new IPAuthenticationBroker(broker, allowedIPAddresses);
	}

	public List<String> getAllowedIPAddresses() {
		return allowedIPAddresses;
	}

	public void setAllowedIPAddresses(List<String> allowedIPAddresses) {
		this.allowedIPAddresses = allowedIPAddresses;
	}
	
	

}
