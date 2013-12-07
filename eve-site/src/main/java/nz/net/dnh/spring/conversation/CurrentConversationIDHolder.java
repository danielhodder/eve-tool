/**
 * 
 */
package nz.net.dnh.spring.conversation;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentConversationIDHolder {
	private static final Logger logger = LoggerFactory.getLogger(CurrentConversationIDHolder.class);

	private String conversationID;

	public String getConversationID() {
		return this.conversationID;
	}

	public void setConversationID(String conversationID) {
		if (conversationID == null) {
			conversationID = UUID.randomUUID().toString();
			logger.trace("No conversation ID passed in. Assuming that this is a new conversation with id {}", conversationID);
		}

		// Warn if the CID is getting overwritten
		if (this.conversationID == null) {
			logger.info("Setting this request's conversation ID to: {}", conversationID);
		} else {
			logger.warn("The current request already has a conversation ID of {} but it is being overwritten with {}. This is probably not right",
						this.conversationID, conversationID);
		}

		this.conversationID = conversationID;
	}
}