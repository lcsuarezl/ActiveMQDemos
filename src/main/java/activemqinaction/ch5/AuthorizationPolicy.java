package activemqinaction.ch5;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.Message;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthorizationPolicy implements
		MessageAuthorizationPolicy {
	
    private static final Log LOG = LogFactory.getLog(AuthorizationPolicy.class);

	public boolean isAllowedToConsume(ConnectionContext context, Message message) {
		LOG.info(context.getConnection().getRemoteAddress());
		if (context.getConnection().getRemoteAddress().startsWith("/127.0.0.1")) {
			return true;
		} else {
			return false;
		}
	}

}
