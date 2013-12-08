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

/**
 * A simple holder for the current request's conversation id.
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentConversationIDHolder {
	private static final Logger logger = LoggerFactory.getLogger(CurrentConversationIDHolder.class);

	private String conversationID;

	/**
	 * Returns the current conversation ID.
	 * 
	 * @return The current conversation ID.
	 */
	public String getConversationID() {
		return this.conversationID;
	}

	/**
	 * Sets the current conversation ID. If the {@code conversationID} is {@code null} then a new conversation ID will
	 * be generated.
	 * <p>
	 * If there was already a conversation ID assigned in the request then a warning will be emitted but the
	 * conversation ID will be changed to the new one supplied by the caller. This may cause strange things to happen
	 * when using conversation scoped beans.
	 * <p>
	 * NOTE: This method should only ever be called once in the code base and is designed to be invoked from the
	 * {@link ConversationInterceptor}
	 * 
	 * @param conversationID
	 *            The new conversation ID or {@code null} if a new conversation should be started.
	 */
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