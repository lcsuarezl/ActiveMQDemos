package activemqinaction.ch2.portfolio;

import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

public class Listener implements MessageListener {

	private static final Logger log = LoggerFactory.getLogger(Listener.class);

	public void onMessage(Message message) {
		try {
			MapMessage map = (MapMessage)message;
			String stock = map.getString("stock");
			double price = map.getDouble("price");
			double offer = map.getDouble("offer");
			boolean up = map.getBoolean("up");
			DecimalFormat df = new DecimalFormat( "#,###,###,##0.00" );
			log.info("{} \t {} \t {} \t {}", stock , df.format(price) , df.format(offer) ,(up?"up":"down"));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}
